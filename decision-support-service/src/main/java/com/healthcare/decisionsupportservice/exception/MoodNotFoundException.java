package com.healthcare.decisionsupportservice.exception;

public class MoodNotFoundException extends RuntimeException {
    public MoodNotFoundException(String message) {
        super(message);
    }
}
