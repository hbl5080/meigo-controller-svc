package com.example.website.exceptionHandler;

public class InfoExistedException extends RuntimeException{
    public InfoExistedException(String message){
        super(message);
    }
}
