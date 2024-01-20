package com.example.learning_centre_managment.exception;

public class DataAlreadyExistsException extends RuntimeException {
    public DataAlreadyExistsException(String msg) {
        super(msg);
    }
}
