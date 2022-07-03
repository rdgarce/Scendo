package com.scendodevteam.scendo.exception;

import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationException extends AuthenticationException{

    private int code;

    public CustomAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);

    }

    public CustomAuthenticationException(String msg) {
        super(msg);

    }

    public CustomAuthenticationException(String msg, int code) {
        super(msg);
        this.code = code;

    }

    public int getCode(){
        return this.code;
    }
    
}
