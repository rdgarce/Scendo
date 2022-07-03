package com.scendodevteam.scendo.service;

import com.scendodevteam.scendo.entity.Invito;
import com.scendodevteam.scendo.exception.GenericErrorException;

public interface InvitoSC {
    public Invito salvaInvito(String email_invitante, String email_invitato, long uscita) throws GenericErrorException;

    public String rifiutaInvito(String invitato, long uscita) throws GenericErrorException;

    public String accettaInvito(String invitato, long uscita) throws GenericErrorException;
    
    public java.util.List<Invito> leggiInviti(String email) throws GenericErrorException;
}
