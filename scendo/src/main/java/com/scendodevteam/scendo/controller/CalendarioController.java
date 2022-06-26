package com.scendodevteam.scendo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scendodevteam.scendo.entity.Uscita;
import com.scendodevteam.scendo.exception.UtenteGiaRegistrato;
import com.scendodevteam.scendo.service.CalendarioSC;

import java.util.List;

@RestController
public class CalendarioController {
	
	@Autowired
    private CalendarioSC calendarioSC;
	
	@GetMapping("api/calendario")
	public List<Uscita> consultaCalendario(@RequestParam("idUtente") long idUtente)throws UtenteGiaRegistrato{
        
        return calendarioSC.consultaCalendario(idUtente) ;
    }
	
	

}
