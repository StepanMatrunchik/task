package com.task.demo.exceptions;

public class BadExchangeRequest extends RuntimeException{
    public BadExchangeRequest(String message) {
        super(message);
    }
}
