package com.scendodevteam.scendo.exception;

public class GenericErrorException extends Exception{

    private String code;

    public GenericErrorException() {
        super();
    }

    //public GenericErrorException(String message) {
    //    super(message);
    //}

    public GenericErrorException(String message, String code) {
        super(message);
        this.code = code;
    }
    
    public String getCode(){
        return this.code;
    }
}
