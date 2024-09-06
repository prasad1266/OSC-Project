package com.osc.userservice.constatnts;

public class StatusCodes {
    // Registration status codes
    public static final int EMAIL_ALREADY_IN_USE = 30;
    public static final int UNEXPECTED_ERROR = 0;
    public static final int EMAIL_VALID_AND_SENT = 200;
    public static final int EMAIL_NOT_SENT = 220;
    public static final int USER_ID_NOT_EXIST = 1999;
    public static final int OTP_INVALID = 502;
    public static final int OTP_INCORRECT_MAX_REACHED = 301;
    public static final int OTP_VALID = 500;
    public static final int REGISTRATION_SUCCESS = 200;
    public static final int REGISTRATION_FAILURE = 0;

    // Login status codes
    public static final int INVALID_USER_ID = 201;
    public static final int INVALID_PASSWORD = 202;
    public static final int ACTIVE_SESSION_EXISTS = 204;
    public static final int INVALID_CREDENTIALS_MAX_REACHED = 205;
    public static final int LOGIN_SUCCESS = 200;

    // Forgot Password status codes
    public static final int VALID_EMAIL = 200;
    public static final int INVALID_EMAIL = 199;
    public static final int OTP_CORRECT = 200;
    public static final int PASSWORD_SAVED = 200;
    public static final int PASSWORD_NOT_SAVED = 199;

    // Logout status codes
    public static final int LOGOUT_SUCCESS = 200;
}
