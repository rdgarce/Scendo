package com.scendodevteam.scendo.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scendodevteam.scendo.entity.TokenRegistrazione;
import com.scendodevteam.scendo.entity.Utente;
import com.scendodevteam.scendo.exception.GenericErrorException;
import com.scendodevteam.scendo.model.UtenteMD;
import com.scendodevteam.scendo.repository.TokenRegistrazioneDB;
import com.scendodevteam.scendo.repository.UtenteDB;


@Service
public class UtenteSCImplementation implements UtenteSC {

    @Autowired
    private UtenteDB utenteDB;

    @Autowired
    private TokenRegistrazioneDB tokenRegistrazioneDB;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public TokenRegistrazione registerUser(UtenteMD usr) throws GenericErrorException{

        if (utenteDB.findByEmail(usr.getEmail()) != null) {
            throw new GenericErrorException("Esiste già un utente registrato con questa email","RG_001");
        }

        Utente utente = new Utente();

        utente.setCittaDiResidenza(usr.getCittaDiResidenza());
        utente.setCodicePostale(usr.getCodicePostale());
        utente.setCognome(usr.getCognome());
        utente.setDataDiNascita(usr.getDataDiNascita());
        utente.setEmail(usr.getEmail());
        utente.setNome(usr.getNome());
        utente.setPassword(bcryptEncoder.encode(usr.getPassword()));
        utente.setSesso(usr.getSesso());

        utenteDB.save(utente);

        TokenRegistrazione tokenRegistrazione = new TokenRegistrazione(utente, UUID.randomUUID().toString());

        return tokenRegistrazioneDB.save(tokenRegistrazione);

         
    }

    @Override
    public boolean verifyRegistration(Optional<String> token) throws GenericErrorException {
        
        if (!token.isPresent() || token.get() == "") {
            throw new GenericErrorException("Non ha inserito nessun token","VR_001");
        }

        TokenRegistrazione tokenRegistrazione = tokenRegistrazioneDB.findByToken(token.get());

        if (tokenRegistrazione == null)
            throw new GenericErrorException("Il token inserito non è corretto","VR_002");

        if (tokenRegistrazione.scaduto()){
            tokenRegistrazioneDB.delete(tokenRegistrazione);
            throw new GenericErrorException("Il token inserito è scaduto","VR_003");
        }
            

        
        Utente utente = tokenRegistrazione.getUtente();
        utente.setActive(true);
        utenteDB.save(utente);
        return true;
    }

}
