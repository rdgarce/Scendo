package com.scendodevteam.scendo.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.scendodevteam.scendo.exception.GenericErrorException;
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
        
        MessaggioGenerico message = new MessaggioGenerico(sb.toString(),"NF_001");
                

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(message);
    }

    @ExceptionHandler
    public ResponseEntity<MessaggioGenerico> genericErrorHandler(GenericErrorException exception){
        
        MessaggioGenerico message = new MessaggioGenerico(exception.getMessage(),exception.getCode());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(message);
    }
    
}
