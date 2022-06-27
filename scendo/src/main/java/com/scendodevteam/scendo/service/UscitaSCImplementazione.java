package com.scendodevteam.scendo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scendodevteam.scendo.entity.Uscita;
import com.scendodevteam.scendo.entity.Utente;
import com.scendodevteam.scendo.entity.UtenteUscita;
import com.scendodevteam.scendo.exception.GenericError;
import com.scendodevteam.scendo.model.UscitaMD;
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
    public boolean promuoviPartecipante(long id_creatore, String email_partecipante, long id_uscita) throws GenericError {
        
        //check se il creatore esiste
        Optional<Utente> utente_creatore = utenteDB.findById(id_creatore);
        if (!utente_creatore.isPresent())
            throw new GenericError("Nessun utente è associato a questo id: "+ id_creatore);
        

        //check se il partecipante esiste
        Utente utente_partecipante = utenteDB.findByEmail(email_partecipante);
        if (utente_partecipante == null)
            throw new GenericError("Nessun utente è associato a questa email: " + email_partecipante);

        //check se l'uscita esiste
        Optional<Uscita> uscita = uscitaDB.findById(id_uscita);
        if (!uscita.isPresent())
            throw new GenericError("Nessuna uscita associata a questo id: "+ id_uscita);
        
        //check se il creatore è veramente creatore dell'uscita
        List<UtenteUscita> utentiUsciteList = utenteUscitaDB.findByUtenteAndUscita(utente_creatore.get(), uscita.get());
        if(utentiUsciteList.isEmpty() || !utentiUsciteList.get(0).isUtenteCreatore())
            throw new GenericError("Non hai i diritti per eleggere un utente ad Organizzatore");

        //check se il partecipante è veramente partecipante dell'uscita
        List<UtenteUscita> utentiUsciteList_2 = utenteUscitaDB.findByUtenteAndUscita(utente_partecipante, uscita.get());
        if(utentiUsciteList_2.isEmpty())
            throw new GenericError("Non esiste nessun partecipante con email \"" + email_partecipante +"\" per questa uscita");

        UtenteUscita utenteUscita = utentiUsciteList_2.get(0);
        utenteUscita.setUtenteOrganizzatore(true);

        utenteUscitaDB.save(utenteUscita);

        return true;

    }
    
    @Override
    public Uscita creaUscita(UscitaMD uscitaMD, long idUtente) throws GenericError {
    	
        //check se il creatore esiste
        Optional<Utente> utente = utenteDB.findById(idUtente);
        if (!utente.isPresent())
            throw new GenericError("Nessun utente con questo id");

    	Uscita uscita = new Uscita();

        uscita.setTipoUscita(uscitaMD.getTipoUscita());
        uscita.setDataEOra(uscitaMD.getDataEOra());
        uscita.setLocationUscita(uscitaMD.getLocationUscita());
        uscita.setLocationIncontro(uscitaMD.getLocationIncontro());
        uscita.setUscitaPrivata(uscitaMD.isUscitaPrivata());
        uscita.setNumeroMaxPartecipanti(uscitaMD.getNumeroMaxPartecipanti());
        uscita.setDescrizione(uscitaMD.getDescrizione());
        
        UtenteUscita utenteUscita = new UtenteUscita(utente.get(), uscita, true, false);
        
        uscita = uscitaDB.save(uscita);
        utenteUscitaDB.save(utenteUscita);
        
        return uscita;
    }

    @Override
    public List<Uscita> consultaCalendario(long idUtente) throws GenericError { 
    	
    	//check se esiste l'utente
        Optional<Utente> utente = utenteDB.findById(idUtente);
    	if (!utente.isPresent())
            throw new GenericError("Non esiste nessun utente con questo Id");
    	
    	List<UtenteUscita> utentiUsciteList = utenteUscitaDB.findByUtente(utente.get());
        if (utentiUsciteList.isEmpty()) {
            throw new GenericError("L'utente specificato non partecipa a nessuna uscita attualmente");
        }

        ArrayList<Uscita> uscite = new ArrayList<Uscita>();

        for (UtenteUscita utenteUscita : utentiUsciteList)
            uscite.add(utenteUscita.getUscita());
        
    	return uscite;
    
    }
    






    
}
