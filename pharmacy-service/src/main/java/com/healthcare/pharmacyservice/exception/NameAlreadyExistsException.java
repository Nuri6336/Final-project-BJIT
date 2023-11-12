package com.healthcare.pharmacyservice.exception;

public class NameAlreadyExistsException extends Exception{
    public NameAlreadyExistsException(String MESSAGE) {
        super(MESSAGE);
    }
}
