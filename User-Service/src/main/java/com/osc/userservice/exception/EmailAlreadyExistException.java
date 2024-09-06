package com.osc.userservice.exception;


public class EmailAlreadyExistException extends RuntimeException{

    public EmailAlreadyExistException(){
        super();
    }

    public EmailAlreadyExistException(String msg){
        super(msg);
    }
}

