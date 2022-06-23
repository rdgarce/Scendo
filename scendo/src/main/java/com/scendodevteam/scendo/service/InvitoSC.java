package com.scendodevteam.scendo.service;

import com.scendodevteam.scendo.entity.Invito;

public interface InvitoSC {
    public Invito salvaInvito(Long invitante, Long invitato, Long uscita);
}
