package com.scendodevteam.scendo.model;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessaggioGenerico {

    private HttpStatus status;
    private String message;
    
    //To Be Implemented
    //private int code;

}
