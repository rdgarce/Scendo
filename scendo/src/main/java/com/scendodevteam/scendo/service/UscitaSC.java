package com.scendodevteam.scendo.service;

import com.scendodevteam.scendo.entity.Uscita;
import com.scendodevteam.scendo.exception.GenericErrorException;
import com.scendodevteam.scendo.model.UscitaMD;

public interface UscitaSC {
    
    public boolean promuoviPartecipante(String email_creatore, String partecipante, long uscita) throws GenericErrorException;
    
    public Uscita creaUscita(String email, UscitaMD uscitaMD) throws GenericErrorException;

    public java.util.List<Uscita> consultaCalendario(String email) throws GenericErrorException;
}
