package com.scendodevteam.scendo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scendodevteam.scendo.entity.Invito;
import com.scendodevteam.scendo.entity.Uscita;
import com.scendodevteam.scendo.entity.Utente;
import com.scendodevteam.scendo.entity.UtenteUscita;
import com.scendodevteam.scendo.exception.GenericErrorException;
import com.scendodevteam.scendo.model.InUscitaMD;
import com.scendodevteam.scendo.model.OutPartecipanteMD;
import com.scendodevteam.scendo.model.OutUscitaMD;
import com.scendodevteam.scendo.repository.InvitoDB;
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

    @Autowired
    InvitoDB invitoDB;

    @Override
    public boolean promuoviPartecipante(String email_creatore, String email_partecipante, long id_uscita) throws GenericErrorException {
        
        //check se il creatore esiste
        Utente utente_creatore = utenteDB.findByEmail(email_creatore);
        if (utente_creatore == null)
            throw new GenericErrorException("Nessun utente è associato a questa email: "+ email_creatore, "PP_001");
        

        //check se il partecipante esiste
        Utente utente_partecipante = utenteDB.findByEmail(email_partecipante);
        if (utente_partecipante == null)
            throw new GenericErrorException("Nessun utente è associato a questa email: " + email_partecipante, "PP_002");

        //check se l'uscita esiste
        Optional<Uscita> uscita = uscitaDB.findById(id_uscita);
        if (!uscita.isPresent())
            throw new GenericErrorException("Nessuna uscita associata a questo id: "+ id_uscita, "PP_003");
        
        //check se il creatore è veramente creatore dell'uscita
        List<UtenteUscita> utentiUsciteList = utenteUscitaDB.findByUtenteAndUscita(utente_creatore, uscita.get());
        if(utentiUsciteList.isEmpty() || !utentiUsciteList.get(0).isUtenteCreatore())
            throw new GenericErrorException("Non hai i diritti per eleggere un utente ad Organizzatore", "PP_004");

        //check se il partecipante è veramente partecipante dell'uscita
        List<UtenteUscita> utentiUsciteList_2 = utenteUscitaDB.findByUtenteAndUscita(utente_partecipante, uscita.get());
        if(utentiUsciteList_2.isEmpty())
            throw new GenericErrorException("Non esiste nessun partecipante con email \"" + email_partecipante +"\" per questa uscita", "PP_005");

        UtenteUscita utenteUscita = utentiUsciteList_2.get(0);
        utenteUscita.setUtenteOrganizzatore(true);

        utenteUscitaDB.save(utenteUscita);

        return true;

    }
    
    @Override
    public Uscita creaUscita(String email, InUscitaMD uscitaMD) throws GenericErrorException {
    	
    
    	//check se il partecipante esiste
        Utente utente = utenteDB.findByEmail(email);
        if (utente == null)
           throw new GenericErrorException("Nessun utente è associato a questa email: " + email,"CU_001");


    	Uscita uscita = new Uscita();

        uscita.setTipoUscita(uscitaMD.getTipoUscita());
        uscita.setDataEOra(uscitaMD.getDataEOra());
        uscita.setLocationUscita(uscitaMD.getLocationUscita());
        uscita.setLocationIncontro(uscitaMD.getLocationIncontro());
        uscita.setUscitaPrivata(uscitaMD.isUscitaPrivata());
        uscita.setNumeroMaxPartecipanti(uscitaMD.getNumeroMaxPartecipanti());
        uscita.setDescrizione(uscitaMD.getDescrizione());
        
        UtenteUscita utenteUscita = new UtenteUscita(utente, uscita, true, false);
        
        uscita = uscitaDB.save(uscita);
        utenteUscitaDB.save(utenteUscita);
        
        return uscita;
    }

    @Override
    public List<Long> consultaCalendario(String email) throws GenericErrorException { 
    	
        /*	
        //check se esiste l'utente
        Optional<Utente> utente = utenteDB.findById(idUtente);
    	if (!utente.isPresent())
            throw new GenericError("Non esiste nessun utente con questo Id");
        */
    	
    	Utente utente = utenteDB.findByEmail(email);
    	
    	List<UtenteUscita> utentiUsciteList = utenteUscitaDB.findByUtente(utente);
        
        /*
        if (utentiUsciteList.isEmpty()) {
            throw new GenericErrorException("L'utente specificato non partecipa a nessuna uscita attualmente","CC_001");
        }
        */

        ArrayList<Long> uscite = new ArrayList<Long>();

        for (UtenteUscita utenteUscita : utentiUsciteList)
            uscite.add(utenteUscita.getUscita().getIdUscita());
        
    	return uscite;
    
    }

    @Override
    public OutUscitaMD infoUscita(String email, long id_uscita, boolean partecipanti) throws GenericErrorException {
        
        //check se l'uscita esiste
        Optional<Uscita> uscita = uscitaDB.findById(id_uscita);
        if (!uscita.isPresent())
            throw new GenericErrorException("Nessuna uscita associata a questo id: "+ id_uscita, "IU_001");
        
        Utente utente = utenteDB.findByEmail(email);

        //Magari è stati invitato all'uscita quindi se è stato invitato 
        //può vedere i particolari dell'uscita anche se non vi partecipa ancora
        List<UtenteUscita> utente_uscita = utenteUscitaDB.findByUtenteAndUscita(utente, uscita.get());
        List<Invito> invito_list = invitoDB.findByUscitaAndUtenteInvitato(uscita.get(), utente);

        if (utente_uscita.isEmpty() && invito_list.isEmpty()) {
            throw new GenericErrorException("L'utente non è autorizzato a vedere i dettagli dell'uscita "+ id_uscita + " in quanto non vi partecipa", "IU_002");
        }
        
        
        OutUscitaMD oUscitaMD = new OutUscitaMD();

        oUscitaMD.setDataEOra(uscita.get().getDataEOra());
        oUscitaMD.setDescrizione(uscita.get().getDescrizione());
        oUscitaMD.setIdUscita(uscita.get().getIdUscita());
        oUscitaMD.setLocationIncontro(uscita.get().getLocationIncontro());
        oUscitaMD.setLocationUscita(uscita.get().getLocationUscita());
        oUscitaMD.setNumeroMaxPartecipanti(uscita.get().getNumeroMaxPartecipanti());
        oUscitaMD.setTipoUscita(uscita.get().getTipoUscita());
        oUscitaMD.setUscitaPrivata(uscita.get().isUscitaPrivata());

        if (!partecipanti)
            return oUscitaMD;
        
        List<UtenteUscita> utenteUscita_list = utenteUscitaDB.findByUscita(uscita.get());

        ArrayList<OutPartecipanteMD> outPartecipanteMD_list = new ArrayList<OutPartecipanteMD>();
        

        for (UtenteUscita utenteUscita : utenteUscita_list) {
            
            OutPartecipanteMD temp = new OutPartecipanteMD();
            
            temp.setCognome(utenteUscita.getUtente().getCognome());
            temp.setEmail(utenteUscita.getUtente().getEmail());
            temp.setNome(utenteUscita.getUtente().getNome());
            temp.setUtenteCreatore(utenteUscita.isUtenteCreatore());
            temp.setUtenteOrganizzatore(utenteUscita.isUtenteOrganizzatore());
            outPartecipanteMD_list.add(temp);

        }

        oUscitaMD.setPartecipanti(outPartecipanteMD_list);

        return oUscitaMD;
        

        
    }
    






    
}
