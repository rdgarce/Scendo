package com.scendodevteam.scendo.service;

import com.scendodevteam.scendo.entity.Invito;
import com.scendodevteam.scendo.repository.InvitoDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvitoSCImplementation implements InvitoSC{

    @Autowired
    private InvitoDB invitoDB;
    @Override
    public Invito salvaInvito(Invito invito) {
        return invitoDB.save(invito);
    }
}
