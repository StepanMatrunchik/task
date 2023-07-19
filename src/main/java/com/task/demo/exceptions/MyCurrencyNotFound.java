package com.task.demo.exceptions;

public class MyCurrencyNotFound extends  RuntimeException{
    public MyCurrencyNotFound(String message){
        super(message);
    }
}
