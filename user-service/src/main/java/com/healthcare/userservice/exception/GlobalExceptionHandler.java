package com.healthcare.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler({UserAlreadyExistsException.class})
    public ResponseEntity<?> handleNameAlreadyExistsException(UserAlreadyExistsException MESSAGE) {
        return new ResponseEntity<>(MESSAGE.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UserNotExistsException.class})
    public ResponseEntity<?> handleValueNotFoundException(UserNotExistsException MESSAGE) {
        return new ResponseEntity<>(MESSAGE.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
