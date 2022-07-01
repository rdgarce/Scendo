package com.scendodevteam.scendo.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import com.scendodevteam.scendo.entity.Invito;
import com.scendodevteam.scendo.entity.Uscita;
import com.scendodevteam.scendo.exception.GenericError;
import com.scendodevteam.scendo.model.UscitaMD;
import com.scendodevteam.scendo.service.InvitoSC;
import com.scendodevteam.scendo.service.UscitaSC;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UscitaController {

    @Autowired
    UscitaSC uscitaSC;

    @Autowired
    InvitoSC invitoSC;


    @PostMapping("/api/uscita/{idUscita}/promuovi-partecipante")
    public String promuoviPartecipante(@RequestParam @Email(message = "Email incorretta") String email_partecipante,
                                       @PathVariable long idUscita) throws GenericError{
        
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        uscitaSC.promuoviPartecipante(currentUser.getUsername(),email_partecipante,idUscita);
        return "Utente promosso ad Organizzatore";
    }
    
    @PostMapping("/api/crea-uscita")
    public String creaUscita(@Valid @RequestBody UscitaMD uscitaMD) throws GenericError{

    	User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
    	uscitaSC.creaUscita(currentUser.getUsername(),uscitaMD);
        
        return "Uscita creata con successo";
    }

    @GetMapping("api/calendario-uscite")
	public List<Uscita> consultaCalendario()throws GenericError{
        
    	User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
    	return uscitaSC.consultaCalendario((currentUser.getUsername()));
    }
    

    @GetMapping("api/leggi-inviti")
	public List<Invito> leggiInviti()
			                        throws GenericError{
        
    	User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return invitoSC.leggiInviti(currentUser.getUsername());
    }


    @PostMapping("/api/uscita/{idUscita}/invita-partecipante") //localhost:8080/api/uscita/{idUScita}/invita-partecipante?email_invitato=email_invitato
    public Invito salvaInvito(@RequestParam(name = "email_invitato") @Email(message = "Email incorretta") String email_invitato,
                              @PathVariable long idUscita
                              ) throws GenericError {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return invitoSC.salvaInvito(currentUser.getUsername(), email_invitato, idUscita);
    }

    @DeleteMapping("/api/rifiuta-invito") //localhost:8080/api/invito/rifiuta-invito?uscita=id_uscita
    public String rifiutaInvito(@RequestParam(name = "uscita") long uscita) throws GenericError {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return invitoSC.rifiutaInvito(currentUser.getUsername(), uscita);
    }

    @PostMapping("/api/accetta-invito") //localhost:8080/api/invito/accetta-invito?uscita=id_uscita
    public String accettaInvito(@RequestParam(name = "uscita") long uscita) throws GenericError {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return invitoSC.accettaInvito(currentUser.getUsername(), uscita);
    }
    

}
