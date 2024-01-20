package com.example.learning_centre_managment.exception;


public class SendVerificationCodeException extends RuntimeException{
    public SendVerificationCodeException(String message) {
        super(message);
    }
}