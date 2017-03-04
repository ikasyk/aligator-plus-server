package com.aligatorplus.controller.profile;

import com.aligatorplus.core.response.AbstractResponse;
import com.aligatorplus.core.response.code.CommonResponseCode;
import com.aligatorplus.core.response.code.ResponseCode;

public class ProfileInfoResponse extends AbstractResponse {
    private String login;
    private String email;

    public ProfileInfoResponse(String login, String email) {
        super(CommonResponseCode.OK);
        this.login = login;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
