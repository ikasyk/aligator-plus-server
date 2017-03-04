package com.aligatorplus.core.response.code;

public enum CommonResponseCode implements ResponseCode {
    OK(1),
    UNDETECTED(2);

    private int code;

    CommonResponseCode(int code) {
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
