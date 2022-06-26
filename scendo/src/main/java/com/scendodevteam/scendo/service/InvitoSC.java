package com.scendodevteam.scendo.service;

import com.scendodevteam.scendo.entity.Invito;
import com.scendodevteam.scendo.exception.GenericError;

public interface InvitoSC {
    public Invito salvaInvito(long invitante, String email_invitato, long uscita) throws GenericError;

    public String rifiutaInvito(long invitato, long uscita) throws GenericError;

    public String accettaInvito(long invitato, long uscita) throws GenericError;
}
