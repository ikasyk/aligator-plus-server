package com.aligatorplus.core.response;

import com.aligatorplus.core.response.code.CommonResponseCode;
import com.aligatorplus.core.response.code.ResponseCode;
import com.fasterxml.jackson.annotation.JsonGetter;

public class AbstractResponse {
    ResponseCode status = CommonResponseCode.UNDETECTED;

    public AbstractResponse(ResponseCode status) {
        this.status = status;
    }

    @JsonGetter("status")
    public int getStatus() {
        return status.getCode();
    }

    public void setStatus(ResponseCode status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AbstractResponse{" +
                "status=" + status +
                '}';
    }
}
