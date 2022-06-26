package com.scendodevteam.scendo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scendodevteam.scendo.entity.UtenteUscita;
import com.scendodevteam.scendo.entity.Uscita;
import com.scendodevteam.scendo.exception.UtenteGiaRegistrato;
import com.scendodevteam.scendo.repository.UscitaDB;
import com.scendodevteam.scendo.repository.UtenteDB;
import com.scendodevteam.scendo.repository.UtenteUscitaDB;

@Service
public class CalendarioSCImplementation implements CalendarioSC {
	
	@Autowired
    private UtenteUscitaDB utenteUscitaDB;
    @Autowired
    private UscitaDB uscitaDB;
    
    @Override
    public java.util.List<Uscita> consultaCalendario(long idUtente) throws UtenteGiaRegistrato { 
    	
    	//check se esiste almeno un'uscita riferita a quell'utente
    	if (!uscitaDB.existsById(idUtente))
            throw new UtenteGiaRegistrato("L'utente in questione non ha uscite in programma");
    	
    	List<Uscita> utentiUsciteList = utenteUscitaDB.findByUtente(idUtente);
        
    	return utentiUsciteList;
    
    }
    

}
