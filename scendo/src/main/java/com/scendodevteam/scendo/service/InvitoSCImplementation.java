package com.scendodevteam.scendo.service;

import com.scendodevteam.scendo.entity.Invito;
import com.scendodevteam.scendo.entity.Uscita;
import com.scendodevteam.scendo.entity.Utente;
import com.scendodevteam.scendo.repository.InvitoDB;
import com.scendodevteam.scendo.repository.UscitaDB;
import com.scendodevteam.scendo.repository.UtenteDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvitoSCImplementation implements InvitoSC{

    @Autowired
    private InvitoDB invitoDB;

    @Autowired
    private UtenteDB utenteDB;

    @Autowired
    private UscitaDB uscitaDB;

    @Override
    public Invito salvaInvito(Long invitante, Long invitato, Long id_uscita) {
        Utente utenteInvitante = utenteDB.getReferenceById(invitante);
        Utente utenteInvitato = utenteDB.getReferenceById(invitato);
        Uscita uscita = uscitaDB.getReferenceById(id_uscita);
        Invito invito = new Invito(utenteInvitante, utenteInvitato, uscita);
        return invitoDB.save(invito);
    }
}
