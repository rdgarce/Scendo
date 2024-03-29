package com.scendodevteam.scendo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.scendodevteam.scendo.exception.GenericErrorException;
import com.scendodevteam.scendo.model.MessaggioGenerico;
import com.scendodevteam.scendo.service.UtenteSC;


@RestController
@CrossOrigin(origins = {"http://scendo.it","https://scendo.it","http://www.scendo.it","https://www.scendo.it"})
public class UtenteController {

    @Autowired
    private UtenteSC utenteSC;

    @GetMapping("/api/utente/{email_utente}")
    public MessaggioGenerico infoUtente(@PathVariable(name = "email_utente") String email_utente) throws GenericErrorException{

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return new MessaggioGenerico(utenteSC.infoUtente(email_utente),"IT_000");
    }

    @GetMapping("/api/utente/me")
    public MessaggioGenerico mieInfo() throws GenericErrorException{

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return new MessaggioGenerico(utenteSC.mieInfo(currentUser.getUsername()),"MI_000");
    }
    
}
