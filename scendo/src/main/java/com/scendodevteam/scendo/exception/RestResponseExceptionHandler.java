package com.scendodevteam.scendo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.scendodevteam.scendo.entity.MessaggioErrore;

@ControllerAdvice
public class RestResponseExceptionHandler{

    @ExceptionHandler
    public ResponseEntity<MessaggioErrore> notValidFieldsHandler(MethodArgumentNotValidException exception){

        StringBuilder sb = new StringBuilder();
        for (ObjectError error : exception.getBindingResult().getAllErrors()) {
			sb.append(error.getDefaultMessage()).append(", ");
		}
        sb.delete(sb.length()-2,sb.length());
        
        MessaggioErrore message = new MessaggioErrore(HttpStatus.BAD_REQUEST,sb.toString());
                

        return ResponseEntity.status(message.getStatus())
                .body(message);
    }
    
}
