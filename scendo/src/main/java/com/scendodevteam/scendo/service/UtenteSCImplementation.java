package com.scendodevteam.scendo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scendodevteam.scendo.entity.Utente;
import com.scendodevteam.scendo.repository.UtenteDB;


@Service
public class UtenteSCImplementation implements UtenteSC {

    @Autowired
    private UtenteDB utenteDB;

    @Override
    public Utente save(Utente usr) {

        
        return utenteDB.save(usr);
    }

}
