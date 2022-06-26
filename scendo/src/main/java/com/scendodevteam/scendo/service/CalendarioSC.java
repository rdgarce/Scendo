package com.scendodevteam.scendo.service;

import com.scendodevteam.scendo.entity.Uscita;
import com.scendodevteam.scendo.entity.Utente;
import com.scendodevteam.scendo.entity.UtenteUscita;
import com.scendodevteam.scendo.exception.UtenteGiaRegistrato;

public interface CalendarioSC {
	
	public java.util.List<Uscita> consultaCalendario(long idUtente) throws UtenteGiaRegistrato;

}
