package com.scendodevteam.scendo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UscitaController {
    
    @GetMapping("/api/")
    public String hello(){
        return "Ciao";
    }

}
