package com.osc.userservice.exception;

public class MaxOtpAttemptsReachedException extends RuntimeException{
    public MaxOtpAttemptsReachedException(){
        super();
    }

    public MaxOtpAttemptsReachedException(String msg){
        super(msg);
    }
}
