package com.example.springbootintro.exception;

public class RegistrationException extends RuntimeException {
    public RegistrationException(String message) {
        super(message);
    }
}
