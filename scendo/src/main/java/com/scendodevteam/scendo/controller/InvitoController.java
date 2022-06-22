package com.scendodevteam.scendo.controller;

import com.scendodevteam.scendo.entity.Invito;
import com.scendodevteam.scendo.service.InvitoSC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvitoController {

    @Autowired
    private InvitoSC invitoSC;

    @PostMapping("/api/invito")
    public Invito salvaInvito(@RequestBody Invito invito){
        return invitoSC.salvaInvito(invito);
    }
}
