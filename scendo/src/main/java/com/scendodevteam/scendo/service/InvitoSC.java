package com.scendodevteam.scendo.service;

import com.scendodevteam.scendo.entity.Invito;
import com.scendodevteam.scendo.exception.UtenteGiaRegistrato;

public interface InvitoSC {
    public Invito salvaInvito(Long invitante, String email_invitato, Long uscita) throws UtenteGiaRegistrato;

    public String rifiutaInvito(Long invitato, Long uscita) throws UtenteGiaRegistrato;

    public String accettaInvito(Long invitato, Long uscita) throws UtenteGiaRegistrato;
}
