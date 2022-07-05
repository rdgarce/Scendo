package com.scendodevteam.scendo.exception.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scendodevteam.scendo.exception.CustomAuthenticationException;
import com.scendodevteam.scendo.model.MessaggioGenerico;

@Component
public class AuthExceptionHandler implements AuthenticationEntryPoint{

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        MessaggioGenerico messaggioGenerico = new MessaggioGenerico();

        //Rimane da gestire la creazione di un json di risposta 
        //e di gestire quando manca il token perché è invalido 
        //e quando viene lanciata l'eccezzione standard da spring quando il token manca proprio
        
        
        if (authException.getCause() instanceof CustomAuthenticationException) {

            messaggioGenerico.setCode(((CustomAuthenticationException)authException.getCause()).getCode());
            messaggioGenerico.setMessage(((CustomAuthenticationException)authException.getCause()).getMessage());
            
        }
        
        if (authException instanceof InsufficientAuthenticationException) {

            messaggioGenerico.setCode("LG_001");
            messaggioGenerico.setMessage("Non è stato possibile effettuare l'autenticazione con il token fornito");
            
        }

        if (authException instanceof BadCredentialsException) {

            messaggioGenerico.setCode("LG_004");
            messaggioGenerico.setMessage("Password Errata");
            
        }

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(messaggioGenerico));



        
    }
    
}
