package com.aligatorplus.controller;

import com.aligatorplus.db.service.UserService;
import com.aligatorplus.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Project AligatorPlus
 * Created by igor, 05.02.17 09:25
 */

@RestController
@Transactional
@RequestMapping("/hello")
public class SignIn {
    private static final Logger logger = LoggerFactory.getLogger(SignIn.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/world")
    @ResponseBody
    public User hello() {
        logger.error("IN HELLO");
//        User user = userService.findAll();

//        return userService.findAll();
        return userService.findById(4L);
//        return userService.findByEmail("kasyk3@gmail.com");
    }
}
