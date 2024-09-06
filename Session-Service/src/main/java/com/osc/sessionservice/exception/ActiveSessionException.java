package com.osc.sessionservice.exception;

public class ActiveSessionException extends RuntimeException{
    public ActiveSessionException(){
        super();
    }

    public ActiveSessionException(String msg){
        super(msg);
    }
}
