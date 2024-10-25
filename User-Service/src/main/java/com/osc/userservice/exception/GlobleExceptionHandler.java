package com.osc.userservice.exception;


import com.osc.userservice.constatnts.StatusCodes;
import com.osc.userservice.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobleExceptionHandler {
    private static final Logger logger = LogManager.getLogger(GlobleExceptionHandler.class);

    private Response response;

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<Response> invalidPasswordExceptionHandler(EmailNotFoundException emailNotFoundException) {
        response = new Response(null, StatusCodes.INVALID_EMAIL);
        logger.error("Email not found: {}", emailNotFoundException.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<Response> invalidUserIDExceptionHandler(EmailAlreadyExistException invalidUserIDException) {
        response = new Response(null, StatusCodes.EMAIL_ALREADY_IN_USE);
        logger.error("Email already exists: {}", invalidUserIDException.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(InvalidOTPException.class)
    public ResponseEntity<Response> ActiveSessionExceptionHandler(InvalidOTPException invalidOTPException) {
        response = new Response("Invalid OTP", StatusCodes.OTP_INVALID);
        logger.error("Invalid OTP: {}", invalidOTPException.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @ExceptionHandler(MaxOtpAttemptsReachedException.class)
    public ResponseEntity<Response> maxOtpAttemptsReachedExceptionHandler(MaxOtpAttemptsReachedException ex) {
        Response response = new Response("Maximum OTP attempts reached", StatusCodes.OTP_INCORRECT_MAX_REACHED);
        logger.error("Maximum OTP attempts reached: {}", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @ExceptionHandler(UserIdNotFoundException.class)
    public ResponseEntity<Response> userIdNotFoundExceptionHandler(UserIdNotFoundException ex) {
        Response response = new Response("User ID does not exist", StatusCodes.USER_ID_NOT_EXIST);
        logger.error("User ID not found: {}", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Response> unexpectedError (Exception ex) {
//        Response response = new Response("", StatusCodes.UNEXPECTED_ERROR);
//        logger.error("Unexpected Error: {}", ex.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

}

