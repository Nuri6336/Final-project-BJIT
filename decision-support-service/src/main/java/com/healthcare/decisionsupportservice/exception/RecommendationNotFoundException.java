package com.healthcare.decisionsupportservice.exception;

public class RecommendationNotFoundException extends RuntimeException {
    public RecommendationNotFoundException(String message) {
        super(message);
    }
}
