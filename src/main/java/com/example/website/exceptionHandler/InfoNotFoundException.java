package com.example.website.exceptionHandler;

public class InfoNotFoundException extends RuntimeException{
    public InfoNotFoundException(String message){
        super(message);
    }

    public InfoNotFoundException(){
        super();
    }
}
