package com.scendodevteam.scendo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.scendodevteam.scendo.entity.Utente;
import com.scendodevteam.scendo.service.UtenteSC;

@RestController
public class AccessManagerController{

    @Autowired
    private UtenteSC utenteSC;

    @PostMapping("/api/register")
    public Utente registerUser(@Valid @RequestBody Utente usr){

        return utenteSC.save(usr);
    }

    
    
}
