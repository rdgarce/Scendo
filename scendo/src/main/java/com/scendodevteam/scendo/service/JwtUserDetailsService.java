package com.scendodevteam.scendo.service;

import java.util.ArrayList;

import com.scendodevteam.scendo.entity.Utente;
import com.scendodevteam.scendo.repository.UtenteDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UtenteDB utenteDB;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utente utente = utenteDB.findByEmail(email);
        if (utente == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(utente.getEmail(), utente.getPassword(),
                new ArrayList<>());
    }

}
