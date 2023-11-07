package com.healthcare.userservice.constants;

public class AppConstants {
    public static final String TOKEN_SECRET = "S3cr3tK3yForJWT$2023";
    public static final long EXPIRATION_TIME = 864000000; //10 days

    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_PATIENT = "PATIENT";
    public static final String ROLE_DOCTOR = "DOCTOR";
    public static final String USER_UNAUTHORIZED = "You are not authorized to access this!";
    public static final String USER_NOT_FOUND = "User does not exists!";
    public static final String TOKEN_INVALID = "Token validation problem!";


}
