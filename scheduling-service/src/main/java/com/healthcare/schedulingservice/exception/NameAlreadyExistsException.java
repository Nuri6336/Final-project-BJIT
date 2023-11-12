package com.healthcare.schedulingservice.exception;

public class NameAlreadyExistsException extends Exception{
    public NameAlreadyExistsException(String MESSAGE) {
        super(MESSAGE);
    }
}
