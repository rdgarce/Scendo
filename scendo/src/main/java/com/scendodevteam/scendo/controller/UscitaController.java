package com.scendodevteam.scendo.controller;

import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scendodevteam.scendo.exception.UtenteGiaRegistrato;
import com.scendodevteam.scendo.service.UscitaSC;
import java.util.Date;

@RestController
public class UscitaController {

    @Autowired
    UscitaSC uscitaSC;

    @PutMapping("/api/uscita/promuovi-partecipante")
    public String promuoviPartecipante(@RequestParam long id_creatore,
                                       @RequestParam @Email(message = "Email incorretta") String email_partecipante,
                                       @RequestParam long uscita) throws UtenteGiaRegistrato{

        uscitaSC.promuoviPartecipante(id_creatore,email_partecipante,uscita);
        
        return "C";
    }
    
    @PostMapping("/api/uscita/crea-uscita")  
    public String creaUscita(@RequestParam (name="tipoUscita") String tipoUscita,                                  
                             @RequestParam (name="dataEora") Date dataEOra,
                             @RequestParam (name="locationUscita") String locationUscita,
                             @RequestParam (name="locationIncontro") String locationIncontro,
                             @RequestParam (name="uscitaPrivata") boolean uscitaPrivata,
                             @RequestParam (name="numeroPartecipanti") int numeroPartecipanti,
                             @RequestParam (name="descrizione") String descrizione
                             )
                                        throws UtenteGiaRegistrato{

        uscitaSC.creaUscita(tipoUscita, dataEOra, locationUscita, locationIncontro, uscitaPrivata, numeroPartecipanti, descrizione);
        
        return "C";
    }

    

}
