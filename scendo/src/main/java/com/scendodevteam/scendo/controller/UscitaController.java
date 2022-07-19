package com.scendodevteam.scendo.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import com.scendodevteam.scendo.exception.GenericErrorException;
import com.scendodevteam.scendo.model.MessaggioGenerico;
import com.scendodevteam.scendo.model.InUscitaMD;
import com.scendodevteam.scendo.service.InvitoSC;
import com.scendodevteam.scendo.service.UscitaSC;

@RestController
@CrossOrigin(origins = "http://44.203.75.74:80")
public class UscitaController {

    @Autowired
    UscitaSC uscitaSC;

    @Autowired
    InvitoSC invitoSC;


    @PostMapping("/api/uscita/{idUscita}/promuovi-partecipante")
    public MessaggioGenerico promuoviPartecipante(@RequestBody Map<String, String> json, @PathVariable long idUscita) throws GenericErrorException{
    
        String email = json.get("email_partecipante");

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        uscitaSC.promuoviPartecipante(currentUser.getUsername(),email,idUscita);
        return new MessaggioGenerico("Utente promosso ad Organizzatore","PP_000");
    }
    
    @PostMapping("/api/crea-uscita")
    public MessaggioGenerico creaUscita(@Valid @RequestBody InUscitaMD uscitaMD) throws GenericErrorException{

    	User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
    	uscitaSC.creaUscita(currentUser.getUsername(),uscitaMD);
        
        return new MessaggioGenerico("Uscita creata con successo","CU_000");
    }

    @GetMapping("/api/uscita/{idUscita}")//?partecipanti=true/false
    public MessaggioGenerico infoUscita(@RequestParam(name = "partecipanti") boolean partecipanti, @PathVariable long idUscita) throws GenericErrorException{
        
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return new MessaggioGenerico(uscitaSC.infoUscita(currentUser.getUsername(), idUscita, partecipanti),"IU_000");
    }

    @GetMapping("api/calendario-uscite")
	public MessaggioGenerico consultaCalendario()throws GenericErrorException{
        
    	User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
    	return new MessaggioGenerico(uscitaSC.consultaCalendario((currentUser.getUsername())), "CC_000");

    }

    @GetMapping("/api/leggi-inviti")
	public MessaggioGenerico leggiInviti() throws GenericErrorException{
        
    	User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new MessaggioGenerico(invitoSC.leggiInviti(currentUser.getUsername()),"LV_000");
    }


    @PostMapping("/api/uscita/{idUscita}/invita-partecipante") //localhost:8080/api/uscita/{idUScita}/invita-partecipante
    public MessaggioGenerico salvaInvito(@RequestBody Map<String, String> json, @PathVariable long idUscita) throws GenericErrorException {

        String email = json.get("email_invitato");

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        invitoSC.salvaInvito(currentUser.getUsername(), email, idUscita);

        return new MessaggioGenerico("Invito inviato correttamente","SV_000");
    }

    @PostMapping("/api/rifiuta-invito") //localhost:8080/api/invito/rifiuta-invito?uscita=id_uscita
    public MessaggioGenerico rifiutaInvito(@RequestParam(name = "uscita") long uscita) throws GenericErrorException {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        return new MessaggioGenerico(invitoSC.rifiutaInvito(currentUser.getUsername(), uscita),"RV_000");
    }

    @PostMapping("/api/accetta-invito") //localhost:8080/api/invito/accetta-invito?uscita=id_uscita
    public MessaggioGenerico accettaInvito(@RequestParam(name = "uscita") long uscita) throws GenericErrorException {

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return new MessaggioGenerico(invitoSC.accettaInvito(currentUser.getUsername(), uscita), "AV_000");
    }
    
}
