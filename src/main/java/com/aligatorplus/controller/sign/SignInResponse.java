package com.aligatorplus.controller.sign;

import com.aligatorplus.core.response.AbstractResponse;
import com.aligatorplus.core.response.code.ResponseCode;

public class SignInResponse extends AbstractResponse {
    public String token;

    SignInResponse(ResponseCode status, String token) {
        super(status);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
