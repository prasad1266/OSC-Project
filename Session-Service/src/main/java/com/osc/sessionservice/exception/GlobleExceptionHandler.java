package com.osc.sessionservice.exception;

import com.osc.sessionservice.constants.StatusCodes;
import com.osc.sessionservice.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobleExceptionHandler {

    private Response response;

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Response> invalidPasswordExceptionHandler(InvalidPasswordException invalidPasswordException) {
        response = new Response(null, StatusCodes.INVALID_PASSWORD);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(InvalidUserIDException.class)
    public ResponseEntity<Response> invalidUserIDExceptionHandler(InvalidUserIDException invalidUserIDException) {
        response = new Response(null, StatusCodes.INVALID_USER_ID);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(ActiveSessionException.class)
    public ResponseEntity<Response> ActiveSessionExceptionHandler(ActiveSessionException activeSessionException) {

        response = new Response(null, StatusCodes.ACTIVE_SESSION_EXISTS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

