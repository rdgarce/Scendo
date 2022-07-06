package com.scendodevteam.scendo.service;

import java.util.List;

import com.scendodevteam.scendo.entity.Invito;
import com.scendodevteam.scendo.exception.GenericErrorException;
import com.scendodevteam.scendo.model.OutInvitoMD;

public interface InvitoSC {
    public Invito salvaInvito(String email_invitante, String email_invitato, long uscita) throws GenericErrorException;

    public String rifiutaInvito(String invitato, long uscita) throws GenericErrorException;

    public String accettaInvito(String invitato, long uscita) throws GenericErrorException;
    
    public List<OutInvitoMD> leggiInviti(String email) throws GenericErrorException;
}
