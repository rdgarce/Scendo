package com.scendodevteam.scendo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scendodevteam.scendo.entity.TokenRegistrazione;
import com.scendodevteam.scendo.exception.UtenteGiaRegistrato;
import com.scendodevteam.scendo.model.UtenteMD;
import com.scendodevteam.scendo.service.UtenteSC;

@RestController
public class AccessManagerController{

    @Autowired
    private UtenteSC utenteSC;

    @PostMapping("/api/register")
    public String registerUser(@Valid @RequestBody UtenteMD usr, HttpServletRequest request) throws UtenteGiaRegistrato{

        TokenRegistrazione tokenRegistrazione = utenteSC.registerUser(usr);
        String url = "http://" + 
                    request.getServerName() + 
                    ":" + 
                    request.getServerPort() + 
                    request.getContextPath() + 
                    "/api/verify-registration?token=" + 
                    tokenRegistrazione.getToken();
        
        return url;
    }

    @GetMapping("/api/verify-registration") //localhost:8080/api/verify-registration?token=tokenstring
    public String verifyRegistration(@RequestParam("token") String token){


        
        return utenteSC.verifyRegistration(token) ? "Account verificato" : "Errore";
    }
    
}
