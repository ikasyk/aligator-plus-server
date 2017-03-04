package com.aligatorplus.controller.sign;

import com.aligatorplus.core.response.code.ResponseCode;


enum SignResponseCode implements ResponseCode {
    USERNAME_ALREADY_EXISTS(51),
    EMAIL_ALREADY_EXISTS(52),
    PASSWORD_NOT_CONFIRMED(53),
    INVALID_LOGIN(54),
    INVALID_EMAIL(55),
    INCORRECT_LOGIN_OR_PASSWORD(56);

    private int code;

    SignResponseCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    @Override
    public String toString() {
        return Integer.toString(this.code);
    }
}