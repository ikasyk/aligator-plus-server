package com.aligatorplus.controller.sites;

import com.aligatorplus.controller.profile.ProfileController;
import com.aligatorplus.core.response.AbstractResponse;
import com.aligatorplus.core.response.ErrorResponse;
import com.aligatorplus.core.response.code.CommonResponseCode;
import com.aligatorplus.db.service.SiteService;
import com.aligatorplus.db.service.UserService;
import com.aligatorplus.model.Site;
import com.aligatorplus.model.User;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Pattern;

@RestController
@Transactional
public class SitesController {
    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    private static final Pattern LINK_REGEX = Pattern.compile("^(https?:\\/\\/)([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$");
    private static final Pattern DOMAIN_REGEX = Pattern.compile("^([a-z0-9]+(-[a-z0-9]+)*\\.)+[a-z]{2,}$");

    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";

    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    @Autowired
    private UserService userService;

    @Autowired
    private SiteService siteService;

    @RequestMapping(value = "/addSite", method = RequestMethod.POST)
    public ResponseEntity addSite(@RequestBody SiteAddRequestBody requestBody) {
        if (validatorFactory.getValidator().validate(requestBody).size() > 0) {
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(CommonResponseCode.UNDETECTED, "Invalid request"), HttpStatus.NOT_ACCEPTABLE);
        }

        boolean isLink = false;

        if (LINK_REGEX.matcher(requestBody.getLink()).find()) {
            isLink = true;
        }

        if (!isLink && !DOMAIN_REGEX.matcher(requestBody.getLink()).find()) {
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(SitesResponseCode.INCORRECT_LINK), HttpStatus.NOT_ACCEPTABLE);
        }

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User retrievedUser = userService.findById(currentUser.getId());

        if (retrievedUser == null) {
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(CommonResponseCode.UNDETECTED), HttpStatus.UNAUTHORIZED);
        }

        try {
            return doAddSite(requestBody, retrievedUser, isLink);

        } catch (IOException exception) {
            logger.error("Jsoup throws exception.");
            exception.printStackTrace();
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(CommonResponseCode.UNDETECTED), HttpStatus.BAD_REQUEST);
        } catch (FeedException exception) {
            logger.error("Rome throws exception.");
            exception.printStackTrace();
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(CommonResponseCode.UNDETECTED), HttpStatus.BAD_REQUEST);
        }
    }

    private ResponseEntity doAddSite(SiteAddRequestBody requestBody, User retrievedUser, boolean isLink)
            throws IOException, FeedException {
        Connection.Response response;

        if (isLink) {
            response = Jsoup.connect(requestBody.getLink()).userAgent(USER_AGENT).execute();
        } else {
            response = Jsoup.connect("http://" + requestBody.getLink()).userAgent(USER_AGENT).execute();

            if (response.statusCode() >= 400) {
                response = Jsoup.connect("https://" + requestBody.getLink()).userAgent(USER_AGENT).execute();
            }
        }

        if (response.statusCode() >= 400) {
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(SitesResponseCode.PAGE_NOT_FOUND), HttpStatus.NOT_FOUND);
        }

        if (Pattern.compile("^(?:application/rss\\+xml|text/xml|application/xml)").matcher(response.contentType()).find()) {
            if (siteService.findByRssLink(response.url().toString()) != null) {
                return new ResponseEntity<AbstractResponse>(new AbstractResponse(CommonResponseCode.OK), HttpStatus.OK);
            }

            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new InputStreamReader(response.url().openStream()));

            Site site = new Site(response.url().getHost(), response.url().toString(), feed.getTitle());

//            siteService.create(site);

            logger.error("Here added site " + site);
            retrievedUser.addSite(site);
            userService.update(retrievedUser);

            return new ResponseEntity<AbstractResponse>(new AbstractResponse(CommonResponseCode.OK), HttpStatus.OK);
        } else {
            Document doc = response.parse();

            Element link = doc.select("link[type=\"application/rss+xml\"]").first();

            if (link == null || link.attr("href") == null) {
                return new ResponseEntity<ErrorResponse>(new ErrorResponse(SitesResponseCode.RSS_NOT_FOUND), HttpStatus.NOT_FOUND);
            }

            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new InputStreamReader(new URL(link.attr("href")).openStream()));

            if (siteService.findByRssLink(feed.getLink()) != null) {
                return new ResponseEntity<AbstractResponse>(new AbstractResponse(CommonResponseCode.OK), HttpStatus.OK);
            }

            Site site = new Site(response.url().getHost(), link.attr("href"), feed.getTitle());

//            siteService.create(site);
            logger.error("Here added 2 site " + site);
            retrievedUser.addSite(site);

            userService.update(retrievedUser);

            return new ResponseEntity<AbstractResponse>(new AbstractResponse(CommonResponseCode.OK), HttpStatus.OK);
        }
    }
}
