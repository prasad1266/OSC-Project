package com.osc.userservice.exception;


public class UserIdNotFoundException extends RuntimeException{

    public UserIdNotFoundException(){
        super();
    }

    public UserIdNotFoundException(String msg){
        super(msg);
    }
}

