package com.aligatorplus.security;

import com.aligatorplus.db.service.UserService;
import com.aligatorplus.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationManager implements AuthenticationManager {
    @Autowired
    private UserService userService;

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;

        String loginOrEmail = (String) auth.getCredentials();
        String password = (String) auth.getPrincipal();

        User user;

        if (loginOrEmail.contains("@")) {
            user = userService.findByEmail(loginOrEmail);
        } else {
            user = userService.findByLogin(loginOrEmail);
        }

        if (user == null || !BCrypt.checkpw(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password.");
        }

        auth = new UsernamePasswordAuthenticationToken(user.getLogin(), null);
        return auth;
    }
}
