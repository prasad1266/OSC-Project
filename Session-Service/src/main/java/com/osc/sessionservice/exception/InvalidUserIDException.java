package com.osc.sessionservice.exception;


public class InvalidUserIDException extends RuntimeException{

    public InvalidUserIDException(){
        super();
    }

    public InvalidUserIDException(String msg){
        super(msg);
    }
}

