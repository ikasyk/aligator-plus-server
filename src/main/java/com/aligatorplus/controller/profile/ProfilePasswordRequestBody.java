package com.aligatorplus.controller.profile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProfilePasswordRequestBody {
    @NotNull
    @Size(min = 8)
    private String password;

    @NotNull
    @Size(min = 8)
    private String newPassword;

    @NotNull
    @Size(min = 8)
    private String newPasswordConfirm;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordConfirm() {
        return newPasswordConfirm;
    }

    public void setNewPasswordConfirm(String newPasswordConfirm) {
        this.newPasswordConfirm = newPasswordConfirm;
    }
}
