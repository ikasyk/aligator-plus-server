package com.aligatorplus.controller.sign;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

public class SignInRequestBody {
    @NotNull
    @Size(min = 3, max = 32)
    @JsonProperty("login")
    private String loginOrEmail;

    @NotNull
    @Size(min = 8)
    private String password;

    public String getLoginOrEmail() {
        return loginOrEmail;
    }

    public void setLoginOrEmail(String loginOrEmail) {
        this.loginOrEmail = loginOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
