package com.scendodevteam.scendo.entity;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessaggioErrore {

    private HttpStatus status;
    private String error_message;

}
