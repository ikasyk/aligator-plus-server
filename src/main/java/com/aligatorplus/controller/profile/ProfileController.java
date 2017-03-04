package com.aligatorplus.controller.profile;

import com.aligatorplus.core.response.AbstractResponse;
import com.aligatorplus.core.response.ErrorResponse;
import com.aligatorplus.core.response.code.CommonResponseCode;
import com.aligatorplus.db.service.UserService;
import com.aligatorplus.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

@RestController
@Transactional
public class ProfileController {
    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getProfileInfo", method = RequestMethod.POST)
    public ResponseEntity getProfileInfo() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User retrievedUser = userService.findById(currentUser.getId());

        if (retrievedUser == null) {
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(CommonResponseCode.UNDETECTED), HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<ProfileInfoResponse>(new ProfileInfoResponse(retrievedUser.getLogin(), retrievedUser.getEmail()), HttpStatus.OK);
    }

    @RequestMapping(value = "/setProfileInfo", method = RequestMethod.POST)
    public ResponseEntity setProfileInfo(@RequestBody final ProfilePasswordRequestBody requestBody) {
        if (validatorFactory.getValidator().validate(requestBody).size() > 0) {
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(CommonResponseCode.UNDETECTED, "Invalid request"), HttpStatus.NOT_ACCEPTABLE);
        }

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User retrievedUser = userService.findById(currentUser.getId());

        if (retrievedUser == null) {
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(CommonResponseCode.UNDETECTED), HttpStatus.UNAUTHORIZED);
        }


        if (BCrypt.checkpw(requestBody.getPassword(), retrievedUser.getPassword())) {
            if (requestBody.getNewPassword().equals(requestBody.getNewPasswordConfirm())) {
                String password_bcrypt = BCrypt.hashpw(requestBody.getPassword(), BCrypt.gensalt());

                retrievedUser.setPassword(password_bcrypt);

                userService.update(retrievedUser);

                return new ResponseEntity<AbstractResponse>(new AbstractResponse(CommonResponseCode.OK), HttpStatus.OK);
            } else {
                return new ResponseEntity<ErrorResponse>(new ErrorResponse(ProfileResponseCode.NEW_PASSWORD_NOT_CONFIRMED), HttpStatus.NOT_ACCEPTABLE);
            }
        } else {
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(ProfileResponseCode.OLD_PASSWORD_NOT_CORRECT), HttpStatus.NOT_ACCEPTABLE);
        }
    }
}