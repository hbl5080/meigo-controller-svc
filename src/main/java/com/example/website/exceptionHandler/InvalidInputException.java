package com.example.website.exceptionHandler;

public class InvalidInputException extends RuntimeException{
        public InvalidInputException(String message){
            super(message);
        }
    }

