package com.aligatorplus.controller;

import com.aligatorplus.db.JPAConfig;
import com.aligatorplus.db.service.UserService;
import com.aligatorplus.model.User;
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

    @Autowired
    private UserService userService;

    @RequestMapping("/world")
    public @ResponseBody List<User> hello() {
//        User u = new User(3L, "igojkr", "erwer@ere.er");
//        userService.create(u);
        List<User> users = userService.findAll();
        System.out.println(users);
        return users;
    }
}
