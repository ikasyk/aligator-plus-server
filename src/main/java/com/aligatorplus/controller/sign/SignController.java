package com.aligatorplus.controller.sign;

import com.aligatorplus.core.response.AbstractResponse;
import com.aligatorplus.core.response.ErrorResponse;
import com.aligatorplus.core.response.code.CommonResponseCode;
import com.aligatorplus.db.service.UserService;
import com.aligatorplus.model.User;
import com.aligatorplus.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.regex.Pattern;

@RestController
@Transactional
public class SignController {
    private static final Logger logger = LoggerFactory.getLogger(SignController.class);

    private static final Pattern EMAIL_REGEX = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    private static final Pattern LOGIN_REGEX = Pattern.compile("^[0-9a-zA-Z\\.\\-_]{4,32}$");

    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ResponseEntity signIn(@RequestBody final SignInRequestBody requestBody) {
        if (validatorFactory.getValidator().validate(requestBody).size() > 0) {
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(CommonResponseCode.UNDETECTED, "Invalid request"), HttpStatus.NOT_ACCEPTABLE);
        }

        User possibleUser = userService.findByLogin(requestBody.getLoginOrEmail());

        if (possibleUser == null) {
            possibleUser = userService.findByEmail(requestBody.getLoginOrEmail());

            if (possibleUser == null) {
                return new ResponseEntity<ErrorResponse>(new ErrorResponse(SignResponseCode.INCORRECT_LOGIN_OR_PASSWORD), HttpStatus.UNAUTHORIZED);
            }
        }

        if (BCrypt.checkpw(requestBody.getPassword(), possibleUser.getPassword())) {
            return new ResponseEntity<SignInResponse>(new SignInResponse(CommonResponseCode.OK, jwtUtil.generateToken(possibleUser)), HttpStatus.OK);
        } else {
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(SignResponseCode.INCORRECT_LOGIN_OR_PASSWORD), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity signUp(@RequestBody final SignUpRequestBody requestBody) {
        if (validatorFactory.getValidator().validate(requestBody).size() > 0) {
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(CommonResponseCode.UNDETECTED, "Invalid request"), HttpStatus.NOT_ACCEPTABLE);
        }

        boolean uniqueUsername = userService.findByLogin(requestBody.getLogin()) == null;

        if (!uniqueUsername) {
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(SignResponseCode.USERNAME_ALREADY_EXISTS), HttpStatus.CONFLICT);
        }

        boolean uniqueEmail = userService.findByEmail(requestBody.getEmail()) == null;

        if (!uniqueEmail) {
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(SignResponseCode.EMAIL_ALREADY_EXISTS), HttpStatus.CONFLICT);
        }

        if (!requestBody.getPassword().equals(requestBody.getPasswordConfirm())) {
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(SignResponseCode.PASSWORD_NOT_CONFIRMED), HttpStatus.NOT_ACCEPTABLE);
        }

        if (!LOGIN_REGEX.matcher(requestBody.getLogin()).find()) {
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(SignResponseCode.INVALID_LOGIN), HttpStatus.NOT_ACCEPTABLE);
        }

        if (!EMAIL_REGEX.matcher(requestBody.getEmail()).find()) {
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(SignResponseCode.INVALID_EMAIL), HttpStatus.NOT_ACCEPTABLE);
        }

        String password_bcrypt = BCrypt.hashpw(requestBody.getPassword(), BCrypt.gensalt());

        User user = new User(requestBody.getLogin(), requestBody.getEmail(), password_bcrypt);

        userService.create(user);

        return new ResponseEntity<AbstractResponse>(new AbstractResponse(CommonResponseCode.OK), HttpStatus.OK);
    }
}
