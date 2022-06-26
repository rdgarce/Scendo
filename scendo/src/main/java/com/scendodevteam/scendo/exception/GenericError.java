package com.scendodevteam.scendo.exception;

public class GenericError extends Exception{

    private int code;

    public GenericError() {
        super();
    }

    public GenericError(String message) {
        super(message);
    }

    public GenericError(String message, int code) {
        super(message);
        this.code = code;
    }
    
    int getCode(){
        return this.code;
    }
}
