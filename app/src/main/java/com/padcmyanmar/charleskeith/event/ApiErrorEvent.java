package com.padcmyanmar.charleskeith.event;

public class ApiErrorEvent {
    String errorMsg;

    public ApiErrorEvent(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
