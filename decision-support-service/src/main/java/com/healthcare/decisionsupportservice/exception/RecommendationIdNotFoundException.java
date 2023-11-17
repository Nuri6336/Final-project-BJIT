package com.healthcare.decisionsupportservice.exception;

public class RecommendationIdNotFoundException extends RuntimeException{
    public RecommendationIdNotFoundException(String message) {
        super(message);
    }
}
