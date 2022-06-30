package com.scendodevteam.scendo.service;

import com.scendodevteam.scendo.entity.Invito;
import com.scendodevteam.scendo.exception.GenericError;

public interface InvitoSC {
    public Invito salvaInvito(String email_invitante, String email_invitato, long uscita) throws GenericError;

    public String rifiutaInvito(String invitato, long uscita) throws GenericError;

    public String accettaInvito(String invitato, long uscita) throws GenericError;
}
