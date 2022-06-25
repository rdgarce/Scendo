package com.scendodevteam.scendo.service;

import com.scendodevteam.scendo.entity.Invito;
import com.scendodevteam.scendo.exception.UtenteGiaRegistrato;

public interface InvitoSC {
    public Invito salvaInvito(long invitante, String email_invitato, long uscita) throws UtenteGiaRegistrato;

    public String rifiutaInvito(long invitato, long uscita) throws UtenteGiaRegistrato;

    public String accettaInvito(long invitato, long uscita) throws UtenteGiaRegistrato;
}
