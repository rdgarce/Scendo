package com.scendodevteam.scendo.exception.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthExceptionHandler implements AuthenticationEntryPoint{

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        
        //Rimane da gestire la creazione di un json di risposta 
        //e di gestire quando manca il token perché è invalido 
        //e quando viene lanciata l'eccezzione standard da spring quando il token manca proprio
        System.out.println("ciao");
        
    }
    
}
