package com.healthcare.helpdeskservice.exception;

public class NameAlreadyExistsException extends Exception{
    public NameAlreadyExistsException(String MESSAGE) {
        super(MESSAGE);
    }
}
