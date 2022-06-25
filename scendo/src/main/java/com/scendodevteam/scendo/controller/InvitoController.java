package com.scendodevteam.scendo.controller;

import com.scendodevteam.scendo.entity.Invito;
import com.scendodevteam.scendo.exception.UtenteGiaRegistrato;
import com.scendodevteam.scendo.service.InvitoSC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Email;

@RestController
@Validated
public class InvitoController {

    @Autowired
    private InvitoSC invitoSC;

    @PostMapping("/api/invito") //localhost:8080/api/invito?invitante=id_invitante&email_invitato=email_invitato&uscita=id_uscita
    public Invito salvaInvito(@RequestParam(name = "invitante") Long invitante,
                              @RequestParam(name = "email_invitato") @Email(message = "Email incorretta") String email_invitato,
                              @RequestParam(name = "uscita") Long uscita
                              ) throws UtenteGiaRegistrato {

        return invitoSC.salvaInvito(invitante, email_invitato, uscita);
    }

    @DeleteMapping("/api/invito/rifiuta") //localhost:8080/api/invito/rifiuta?invitato=id_invitato&uscita=id_uscita
    public String rifiutaInvito(@RequestParam(name = "invitato") Long invitato,
                                @RequestParam(name = "uscita") Long uscita) throws UtenteGiaRegistrato {
        return invitoSC.rifiutaInvito(invitato, uscita);
    }

    @PostMapping("/api/invito/accetta") //localhost:8080/api/invito/accetta?invitato=id_invitato&uscita=id_uscita
    public String accettaInvito(@RequestParam(name = "invitato") Long invitato,
                                @RequestParam(name = "uscita") Long uscita) throws UtenteGiaRegistrato {
        return invitoSC.accettaInvito(invitato, uscita);
    }
}
