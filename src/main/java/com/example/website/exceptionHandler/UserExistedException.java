package com.example.website.exceptionHandler;

public class UserExistedException extends RuntimeException{
    public UserExistedException(String message){
        super(message);
    }
}
