package com.scendodevteam.scendo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.scendodevteam.scendo.model.MessaggioGenerico;

@ControllerAdvice
public class RestResponseExceptionHandler{

    @ExceptionHandler
    public ResponseEntity<MessaggioGenerico> notValidFieldsHandler(MethodArgumentNotValidException exception){

        StringBuilder sb = new StringBuilder();
        for (ObjectError error : exception.getBindingResult().getAllErrors()) {
			sb.append(error.getDefaultMessage()).append(", ");
		}
        sb.delete(sb.length()-2,sb.length());
        
        MessaggioGenerico message = new MessaggioGenerico(HttpStatus.BAD_REQUEST,sb.toString());
                

        return ResponseEntity.status(message.getStatus())
                .body(message);
    }

    @ExceptionHandler
    public ResponseEntity<MessaggioGenerico> genericErrorHandler(GenericErrorException exception){
        
        MessaggioGenerico message = new MessaggioGenerico(HttpStatus.BAD_REQUEST,exception.getMessage());

        return ResponseEntity.status(message.getStatus())
                .body(message);
    }
    
}
