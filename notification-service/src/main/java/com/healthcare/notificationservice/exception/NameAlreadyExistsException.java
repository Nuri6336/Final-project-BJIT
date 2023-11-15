package com.healthcare.notificationservice.exception;

public class NameAlreadyExistsException extends Exception{
    public NameAlreadyExistsException(String MESSAGE) {
        super(MESSAGE);
    }
}
