package com.scendodevteam.scendo.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scendodevteam.scendo.entity.TokenRegistrazione;
import com.scendodevteam.scendo.entity.Utente;
import com.scendodevteam.scendo.exception.GenericErrorException;
import com.scendodevteam.scendo.model.InUtenteMD;
import com.scendodevteam.scendo.model.OutUtenteMD;
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
    public TokenRegistrazione registerUser(InUtenteMD usr) throws GenericErrorException{

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
            throw new GenericErrorException("Non hai inserito nessun token","VR_001");
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

    @Override
    public OutUtenteMD infoUtente(String email) throws GenericErrorException {
        

        Utente utente = utenteDB.findByEmail(email);

        if (utente == null || !utente.isActive()) {
            throw new GenericErrorException("Non esiste un utente con questa email","IT_001");
        }

        OutUtenteMD outUtenteMD = new OutUtenteMD();

        outUtenteMD.setNome(utente.getNome());
        outUtenteMD.setCognome(utente.getCognome());
        outUtenteMD.setEmail(utente.getEmail());

        return outUtenteMD;


    }

    @Override
    public OutUtenteMD mieInfo(String email) {
        
        Utente utente = utenteDB.findByEmail(email);

        OutUtenteMD outUtenteMD = new OutUtenteMD();

        outUtenteMD.setNome(utente.getNome());
        outUtenteMD.setCognome(utente.getCognome());
        outUtenteMD.setEmail(utente.getEmail());
        outUtenteMD.setSesso(utente.getSesso());
        outUtenteMD.setDataDiNascita(utente.getDataDiNascita());
        outUtenteMD.setCittaDiResidenza(utente.getCittaDiResidenza());
        outUtenteMD.setCodicePostale(utente.getCodicePostale());

        return outUtenteMD;
    }

}
