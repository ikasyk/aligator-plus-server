package com.aligatorplus.controller.profile;

import com.aligatorplus.core.response.code.ResponseCode;

enum ProfileResponseCode implements ResponseCode {
    OLD_PASSWORD_NOT_CORRECT(101),
    NEW_PASSWORD_NOT_CONFIRMED(102);

    private int code;

    ProfileResponseCode(int code) {
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