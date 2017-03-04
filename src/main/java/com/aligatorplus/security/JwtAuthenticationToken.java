package com.aligatorplus.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private String jwtToken;

    public JwtAuthenticationToken(String jwtToken) {
        super(null, null);
        this.jwtToken = jwtToken;
    }

    public Object getCredentials() {
        return null;
    }

    public Object getPrincipal() {
        return null;
    }

    public String getToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
