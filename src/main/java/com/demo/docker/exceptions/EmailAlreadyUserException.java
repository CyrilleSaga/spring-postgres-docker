package com.demo.docker.exceptions;

public class EmailAlreadyUserException extends RuntimeException {
    public EmailAlreadyUserException(String message) {
        super(message);
    }
}
