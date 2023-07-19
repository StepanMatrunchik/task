package com.task.demo.exceptions;

public class ErrorResponseWithMsg {
    private String errorMessage;

    public ErrorResponseWithMsg(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
