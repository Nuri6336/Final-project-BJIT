package com.healthcare.communityservice.exception;

public class NameAlreadyExistsException extends Exception{
    public NameAlreadyExistsException(String MESSAGE) {
        super(MESSAGE);
    }
}
