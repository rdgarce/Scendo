package com.scendodevteam.scendo.service;

import com.scendodevteam.scendo.entity.Uscita;
import com.scendodevteam.scendo.exception.GenericError;
import com.scendodevteam.scendo.model.UscitaMD;

public interface UscitaSC {
    
    public boolean promuoviPartecipante(long creatore, String partecipante, long uscita) throws GenericError;
    
    public Uscita creaUscita(UscitaMD uscitaMD, long idUtente) throws GenericError;

    public java.util.List<Uscita> consultaCalendario(long idUtente) throws GenericError;
}
