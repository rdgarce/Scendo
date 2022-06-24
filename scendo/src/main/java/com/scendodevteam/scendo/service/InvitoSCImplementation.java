package com.scendodevteam.scendo.service;

import com.scendodevteam.scendo.entity.Invito;
import com.scendodevteam.scendo.entity.Uscita;
import com.scendodevteam.scendo.entity.Utente;
import com.scendodevteam.scendo.entity.UtenteUscita;
import com.scendodevteam.scendo.exception.UtenteGiaRegistrato;
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
    public Invito salvaInvito(Long invitante, String email_invitato, Long id_uscita) throws UtenteGiaRegistrato {
        //check id invitante
        if (!utenteDB.existsById(invitante))
            throw new UtenteGiaRegistrato("Nessun utente è associato a questo id");
        Utente utenteInvitante = utenteDB.getReferenceById(invitante);

        //check email invitato
        Utente utenteInvitato = utenteDB.findByEmail(email_invitato);
        if (utenteInvitato == null)
            throw new UtenteGiaRegistrato("Nessun utente è associato a questa mail");

        //check id uscita
        if (!uscitaDB.existsById(id_uscita))
            throw new UtenteGiaRegistrato("Nessuna uscita è associata a questo id");
        Uscita uscita = uscitaDB.getReferenceById(id_uscita);

        //check se l'invitante ha i diritti per invitare
        

        //check se l'invitato è stato già invitato all'uscita
        if (!invitoDB.findByUscitaAndUtenteInvitato(uscita, utenteInvitato).isEmpty())
            throw new UtenteGiaRegistrato("L'utente è stato già invitato");

        Invito invito = new Invito(utenteInvitante, utenteInvitato, uscita);
        return invitoDB.save(invito);
    }

    @Override
    public String rifiutaInvito(Long invitato, Long id_uscita) throws UtenteGiaRegistrato {

        //check id invitato
        if (!utenteDB.existsById(invitato))
            throw new UtenteGiaRegistrato("Nessun utente è associato a questo id");
        Utente utenteInvitato = utenteDB.getReferenceById(invitato);

        //check id uscita
        if (!uscitaDB.existsById(id_uscita))
            throw new UtenteGiaRegistrato("Nessuna uscita è associata a questo id");
        Uscita uscita = uscitaDB.getReferenceById(id_uscita);

        List<Invito> invito = invitoDB.findByUscitaAndUtenteInvitato(uscita, utenteInvitato);
        if (invito.size() > 0) {
            invitoDB.deleteAll(invito);
            return "Invito eliminato";
        }
        return "Invito inesistente";
    }

    @Override
    public String accettaInvito(Long invitato, Long id_uscita) throws UtenteGiaRegistrato {
        //check id invitato
        if (!utenteDB.existsById(invitato))
            throw new UtenteGiaRegistrato("Nessun utente è associato a questo id");
        Utente utenteInvitato = utenteDB.getReferenceById(invitato);

        //check id uscita
        if (!uscitaDB.existsById(id_uscita))
            throw new UtenteGiaRegistrato("Nessuna uscita è associata a questo id");
        Uscita uscita = uscitaDB.getReferenceById(id_uscita);

        List<Invito> invito = invitoDB.findByUscitaAndUtenteInvitato(uscita, utenteInvitato);
        if (invito.size() > 0) {
            invitoDB.deleteAll(invito);

            UtenteUscita utenteUscita = new UtenteUscita(utenteInvitato, uscita, false, false);
            utenteUscitaDB.save(utenteUscita);

            return "Invito accettato";
        }
        return "Invito inesistente";

    }
}
