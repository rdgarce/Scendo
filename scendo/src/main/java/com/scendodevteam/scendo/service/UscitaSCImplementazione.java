package com.scendodevteam.scendo.service;

import java.util.List;
import java.util.Optional;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scendodevteam.scendo.entity.Uscita;
import com.scendodevteam.scendo.entity.Utente;
import com.scendodevteam.scendo.entity.UtenteUscita;
import com.scendodevteam.scendo.exception.UtenteGiaRegistrato;
import com.scendodevteam.scendo.repository.UscitaDB;
import com.scendodevteam.scendo.repository.UtenteDB;
import com.scendodevteam.scendo.repository.UtenteUscitaDB;

@Service
public class UscitaSCImplementazione implements UscitaSC{

    @Autowired
    UtenteDB utenteDB;

    @Autowired
    UscitaDB uscitaDB;

    @Autowired
    UtenteUscitaDB utenteUscitaDB;

    @Override
    public boolean promuoviPartecipante(long id_creatore, String email_partecipante, long id_uscita) throws UtenteGiaRegistrato {
        
        //check se il creatore esiste
        Optional<Utente> utente_creatore = utenteDB.findById(id_creatore);
        if (!utente_creatore.isPresent())
            throw new UtenteGiaRegistrato("Nessun utente è associato a questo id: "+ id_creatore);
        

        //check se il partecipante esiste
        Utente utente_partecipante = utenteDB.findByEmail(email_partecipante);
        if (utente_partecipante == null)
            throw new UtenteGiaRegistrato("Nessun utente è associato a questa email: " + email_partecipante);

        //check se l'uscita esiste
        Optional<Uscita> uscita = uscitaDB.findById(id_uscita);
        if (!uscita.isPresent())
            throw new UtenteGiaRegistrato("Nessuna uscita associata a questo id: "+ id_uscita);
        
        //check se il creatore è veramente creatore dell'uscita
        List<UtenteUscita> utentiUsciteList = utenteUscitaDB.findByUtenteAndUscita(utente_creatore.get(), uscita.get());
        if(utentiUsciteList.isEmpty() || !utentiUsciteList.get(0).isUtenteCreatore())
            throw new UtenteGiaRegistrato("Non hai i diritti per eleggere un utente ad Organizzatore");

        //check se il partecipante è veramente partecipante dell'uscita
        List<UtenteUscita> utentiUsciteList_2 = utenteUscitaDB.findByUtenteAndUscita(utente_partecipante, uscita.get());
        if(utentiUsciteList_2.isEmpty())
            throw new UtenteGiaRegistrato("Non esiste nessun partecipante con email \"" + email_partecipante +"\" per questa uscita");

        UtenteUscita utenteUscita = utentiUsciteList_2.get(0);
        utenteUscita.setUtenteOrganizzatore(true);

        utenteUscitaDB.save(utenteUscita);

        return true;

    }
    
    @Override
    public Uscita creaUscita(String tipo, Date dataOra, String locUscita, String locIncontro, boolean privata, int nPartecipanti, String descrizione) throws UtenteGiaRegistrato {
    	
    	//Non posso avere il numero di partecipanti uguali a 0
    	if(nPartecipanti==0)
            throw new UtenteGiaRegistrato("Non puoi avere zero partecipanti");
    	
    	//Non posso inserire una data del passato
    	//NB il pattern di default Date in Java è EEE MMM dd HH:mm:ss zzz yyyy es.
    	Date cur = new Date();
    	if((cur.before(dataOra))) 
    	    throw new UtenteGiaRegistrato("Non puoi inserire date passate");
    	
    	Uscita uscita = new Uscita();

        uscita.setTipoUscita(tipo);
        uscita.setDataEOra(dataOra);
        uscita.setLocationUscita(locUscita);
        uscita.setLocationUscita(locIncontro);
        uscita.setUscitaPrivata(privata);
        uscita.setNumeroPartecipanti(nPartecipanti);
        uscita.setDescrizione(descrizione);

        
        return uscitaDB.save(uscita);
    }
    






    
}
