package com.scendodevteam.scendo.controller;

import com.scendodevteam.scendo.entity.Invito;
import com.scendodevteam.scendo.service.InvitoSC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class InvitoController {

    @Autowired
    private InvitoSC invitoSC;

    @PostMapping("/api/invito") //localhost:8080/api/invito?invitante=id_invitante&invitato=id_invitato&uscita=id_uscita
    public Invito salvaInvito(@RequestParam(name = "invitante") Long invitante,
                              @RequestParam(name = "invitato") Long invitato,
                              @RequestParam(name = "uscita") Long uscita
                              ){

        return invitoSC.salvaInvito(invitante, invitato, uscita);
    }
}
