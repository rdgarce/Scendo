package com.scendodevteam.scendo.service;

import org.springframework.stereotype.Service;

import com.scendodevteam.scendo.entity.Utente;
import com.scendodevteam.scendo.exception.CustomAuthenticationException;
import com.scendodevteam.scendo.repository.UtenteDB;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class AuthUserSC implements UserDetailsService{

    @Autowired
    private UtenteDB utenteDB;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        Utente utente = utenteDB.findByEmail(email);
        
        if (utente == null) {
            throw new CustomAuthenticationException("Non esiste un utente con l'email specificata","LG_002");
        }

        if (!utente.isActive()) {
            throw new CustomAuthenticationException("L'utente non è attivo","LG_003");
        }

        return new User(utente.getEmail(),utente.getPassword(), new ArrayList<>());

    }
    
}
