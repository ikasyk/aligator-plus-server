package com.aligatorplus.controller.sites;

import com.aligatorplus.core.response.code.ResponseCode;

public enum SitesResponseCode implements ResponseCode {
    INCORRECT_LINK(151),
    PAGE_NOT_FOUND(152),
    RSS_NOT_FOUND(153);

    private int code;

    SitesResponseCode(int code) {
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
