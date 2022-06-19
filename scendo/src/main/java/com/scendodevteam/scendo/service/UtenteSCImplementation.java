package com.scendodevteam.scendo.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scendodevteam.scendo.entity.TokenRegistrazione;
import com.scendodevteam.scendo.entity.Utente;
import com.scendodevteam.scendo.model.UtenteMD;
import com.scendodevteam.scendo.repository.TokenRegistrazioneDB;
import com.scendodevteam.scendo.repository.UtenteDB;


@Service
public class UtenteSCImplementation implements UtenteSC {

    @Autowired
    private UtenteDB utenteDB;

    @Autowired
    private TokenRegistrazioneDB tokenRegistrazioneDB;

    @Override
    public TokenRegistrazione registerUser(UtenteMD usr) {

        Utente utente = new Utente();

        utente.setCittaDiResidenza(usr.getCittaDiResidenza());
        utente.setCodicePostale(usr.getCodicePostale());
        utente.setCognome(usr.getCognome());
        utente.setDataDiNascita(usr.getDataDiNascita());
        utente.setEmail(usr.getEmail());
        utente.setNome(usr.getNome());
        utente.setPassword(usr.getPassword());
        utente.setSesso(usr.getSesso());

        utenteDB.save(utente);

        TokenRegistrazione tokenRegistrazione = new TokenRegistrazione(utente, UUID.randomUUID().toString());

        return tokenRegistrazioneDB.save(tokenRegistrazione);

         
    }

    @Override
    public boolean verifyRegistration(String token) {
        
        TokenRegistrazione tokenRegistrazione = tokenRegistrazioneDB.findByToken(token);

        if (tokenRegistrazione == null)
            return false;

        if (tokenRegistrazione.scaduto()){
            tokenRegistrazioneDB.delete(tokenRegistrazione);
            return false;
        }
            

        
        Utente utente = tokenRegistrazione.getUtente();
        utente.setActive(true);
        utenteDB.save(utente);
        return true;
    }

}
