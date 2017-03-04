package com.aligatorplus.core.response;

import com.aligatorplus.core.response.code.ResponseCode;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse extends AbstractResponse {
    private String message;

    public ErrorResponse(ResponseCode code, String message) {
        super(code);
        this.message = message;
    }

    public ErrorResponse(ResponseCode code) {
        super(code);
    }
}
