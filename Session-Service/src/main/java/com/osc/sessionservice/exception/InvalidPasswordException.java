package com.osc.sessionservice.exception;

public class InvalidPasswordException extends RuntimeException{

    public InvalidPasswordException(){
        super();
    }

    public InvalidPasswordException(String msg){
        super(msg);
    }
}
