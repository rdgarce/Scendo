
package com.scendodevteam.scendo.service;

import com.scendodevteam.scendo.entity.Invito;
import com.scendodevteam.scendo.entity.Uscita;
import com.scendodevteam.scendo.entity.Utente;
import com.scendodevteam.scendo.entity.UtenteUscita;
import com.scendodevteam.scendo.exception.GenericErrorException;
import com.scendodevteam.scendo.repository.InvitoDB;
import com.scendodevteam.scendo.repository.UscitaDB;
import com.scendodevteam.scendo.repository.UtenteDB;
import com.scendodevteam.scendo.repository.UtenteUscitaDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvitoSCImplementation implements InvitoSC{

    @Autowired
    private InvitoDB invitoDB;
    @Autowired
    private UtenteDB utenteDB;
    @Autowired
    private UscitaDB uscitaDB;
    @Autowired
    private UtenteUscitaDB utenteUscitaDB;

    @Override
    public Invito salvaInvito(String invitante, String email_invitato, long id_uscita) throws GenericErrorException {
        //check invitante
        if (!utenteDB.existsByEmail(invitante))
            throw new GenericErrorException("Nessun utente è associato a questa email");
        Utente utenteInvitante = utenteDB.findByEmail(invitante);

        //check email invitato
        Utente utenteInvitato = utenteDB.findByEmail(email_invitato);
        if (utenteInvitato == null)
            throw new GenericErrorException("Nessun utente è associato a questa mail");

        //check id uscita
        if (!uscitaDB.existsById(id_uscita))
            throw new GenericErrorException("Nessuna uscita è associata a questo id");
        Uscita uscita = uscitaDB.getReferenceById(id_uscita);

        //check se l'invitato è già parte dell'uscita
        List<UtenteUscita> utentiUscite = utenteUscitaDB.findByUtenteAndUscita(utenteInvitato, uscita);
        if(!utentiUscite.isEmpty())
            throw new GenericErrorException("L'utente invitato fa già parte dell'uscita");

        //check se l'invitante ha i diritti per invitare
        List<UtenteUscita> utentiUsciteList = utenteUscitaDB.findByUtenteAndUscita(utenteInvitante, uscita);
        if(utentiUsciteList.isEmpty() || (!utentiUsciteList.get(0).isUtenteCreatore() && !utentiUsciteList.get(0).isUtenteOrganizzatore()))
            throw new GenericErrorException("Non hai i diritti per invitare utenti a questa uscita");

        //check se l'invitato è stato già invitato all'uscita
        if (!invitoDB.findByUscitaAndUtenteInvitato(uscita, utenteInvitato).isEmpty())
            throw new GenericErrorException("L'utente è stato già invitato");

        Invito invito = new Invito(utenteInvitante, utenteInvitato, uscita);
        return invitoDB.save(invito);
    }

    @Override
    public String rifiutaInvito(String invitato, long id_uscita) throws GenericErrorException {

        //check id invitato
        if (!utenteDB.existsByEmail(invitato))
            throw new GenericErrorException("Nessun utente è associato a questa email");
        Utente utenteInvitato = utenteDB.findByEmail(invitato);

        //check id uscita
        if (!uscitaDB.existsById(id_uscita))
            throw new GenericErrorException("Nessuna uscita è associata a questo id");
        Uscita uscita = uscitaDB.getReferenceById(id_uscita);

        List<Invito> invito = invitoDB.findByUscitaAndUtenteInvitato(uscita, utenteInvitato);
        if (invito.size() > 0) {
            invitoDB.deleteAll(invito);
            return "Invito eliminato";
        }
        return "Invito inesistente";
    }

    @Override
    public String accettaInvito(String invitato, long id_uscita) throws GenericErrorException {
        //check id invitato
        if (!utenteDB.existsByEmail(invitato))
            throw new GenericErrorException("Nessun utente è associato a questa email");
        Utente utenteInvitato = utenteDB.findByEmail(invitato);

        //check id uscita
        if (!uscitaDB.existsById(id_uscita))
            throw new GenericErrorException("Nessuna uscita è associata a questo id");
        Uscita uscita = uscitaDB.getReferenceById(id_uscita);

        //check su NumeroMaxPartecipanti
        if(uscita.getNumeroMaxPartecipanti() == utenteUscitaDB.findByUscita(uscita).size())
            throw new GenericErrorException("Numero massimo di partecipanti raggiunto");

        List<Invito> inviti = invitoDB.findByUscitaAndUtenteInvitato(uscita, utenteInvitato);
        if (inviti.size() > 0) {
            invitoDB.deleteAll(inviti);

            UtenteUscita utenteUscita = new UtenteUscita(utenteInvitato, uscita, false, false);
            utenteUscitaDB.save(utenteUscita);

            return "Invito accettato";
        }
        return "Invito inesistente";

    }
    @Override
    public List<Invito> leggiInviti(String email) throws GenericErrorException{
    	         
    	Utente utente = utenteDB.findByEmail(email);
    	List<Invito> InvitiList = invitoDB.findByUtenteInvitato(utente);
        if (InvitiList.isEmpty()) {
            throw new GenericErrorException("Nessun invito in archivio");
        }

    	return InvitiList;
}
    
}
