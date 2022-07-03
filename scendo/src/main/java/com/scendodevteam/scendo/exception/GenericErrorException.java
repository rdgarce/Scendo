package com.scendodevteam.scendo.exception;

public class GenericErrorException extends Exception{

    private int code;

    public GenericErrorException() {
        super();
    }

    public GenericErrorException(String message) {
        super(message);
    }

    public GenericErrorException(String message, int code) {
        super(message);
        this.code = code;
    }
    
    int getCode(){
        return this.code;
    }
}
