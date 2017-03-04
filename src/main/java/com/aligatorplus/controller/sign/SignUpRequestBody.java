package com.aligatorplus.controller.sign;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SignUpRequestBody {
    @NotNull
    @Size(min = 3, max = 32)
    private String login;

    @NotNull
    @Size(min = 5)
    private String email;

    @NotNull
    @Size(min = 8)
    private String password;

    @NotNull
    @Size(min = 8)
    private String passwordConfirm;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @Override
    public String toString() {
        return "SignUpRequestBody{" +
                "login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", passwordConfirm='" + passwordConfirm + '\'' +
                '}';
    }
}
