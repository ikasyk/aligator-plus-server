package com.aligatorplus.security.exception;

import java.io.IOException;

public class JwtTokenMissingException extends IOException {
    public JwtTokenMissingException(String message) {
        super(message);
    }
}
