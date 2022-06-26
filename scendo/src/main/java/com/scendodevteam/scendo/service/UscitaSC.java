package com.scendodevteam.scendo.service;

import com.scendodevteam.scendo.entity.Uscita;
import com.scendodevteam.scendo.entity.Invito;
import com.scendodevteam.scendo.exception.UtenteGiaRegistrato;
import java.util.Date;

public interface UscitaSC {
    
    public boolean promuoviPartecipante(long creatore, String partecipante, long uscita) throws UtenteGiaRegistrato;
    
    public Uscita creaUscita(String tipo, Date dataOra, String locUscita, String locIncontro, boolean privata, int nPartecipanti, String descrizione) throws UtenteGiaRegistrato;
}
