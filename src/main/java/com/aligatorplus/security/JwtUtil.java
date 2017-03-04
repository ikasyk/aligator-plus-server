package com.aligatorplus.security;

import com.aligatorplus.controller.profile.ProfileController;
import com.aligatorplus.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${app.security.jwt.secret}")
    private String secret;

    public User parseToken(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            User u = new User();
            u.setLogin(body.getSubject());
            u.setId(Long.valueOf(body.get("userId").toString()));
            u.setEmail(body.get("email").toString());
            return u;

        } catch (Exception e) {
            return null;
        }
    }

    public String generateToken(User u) {
        logger.error(u.toString());
        Claims claims = Jwts.claims().setSubject(u.getLogin());
        claims.put("userId", u.getId());
        claims.put("email", u.getEmail());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
