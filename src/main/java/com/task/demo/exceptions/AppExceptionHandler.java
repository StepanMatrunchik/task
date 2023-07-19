package com.task.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MyCurrencyNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseWithMsg handleMyCurrencyNotFound(MyCurrencyNotFound e){
        return new ErrorResponseWithMsg(e.getMessage());

    }


    @ExceptionHandler(BadExchangeRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseWithMsg handleBadExchangeRequest(BadExchangeRequest e){
        return new ErrorResponseWithMsg(e.getMessage());
    }
}
