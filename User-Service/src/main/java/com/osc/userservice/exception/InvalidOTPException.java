package com.osc.userservice.exception;

public class InvalidOTPException extends RuntimeException{

    public InvalidOTPException(){
        super();
    }

    public InvalidOTPException(String msg){
        super(msg);
    }
}
