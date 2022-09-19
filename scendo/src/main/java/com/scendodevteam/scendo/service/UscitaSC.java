package com.scendodevteam.scendo.service;

import java.util.List;

import com.scendodevteam.scendo.entity.Uscita;
import com.scendodevteam.scendo.exception.GenericErrorException;
import com.scendodevteam.scendo.model.InUscitaMD;
import com.scendodevteam.scendo.model.OutUscitaMD;

public interface UscitaSC {
    
    public boolean promuoviPartecipante(String email_creatore, String partecipante, long uscita) throws GenericErrorException;
    
    public Uscita creaUscita(String email, InUscitaMD uscitaMD) throws GenericErrorException;

    public List<Long> consultaCalendario(String email) throws GenericErrorException;

    public OutUscitaMD infoUscita(String email,long idUscita, boolean partecipanti) throws GenericErrorException;

    public void abbandonaUscita(String email, long idUscita) throws GenericErrorException;
}
