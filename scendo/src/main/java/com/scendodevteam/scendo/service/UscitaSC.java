package com.scendodevteam.scendo.service;

import com.scendodevteam.scendo.exception.UtenteGiaRegistrato;

public interface UscitaSC {
    
    public boolean promuoviPartecipante(long creatore, String partecipante, long uscita) throws UtenteGiaRegistrato;
}
