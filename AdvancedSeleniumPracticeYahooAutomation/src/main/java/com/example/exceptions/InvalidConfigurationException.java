package com.example.exceptions;

public class InvalidConfigurationException extends RuntimeException{
    public InvalidConfigurationException(String message) {
        super(message);
    }
}
