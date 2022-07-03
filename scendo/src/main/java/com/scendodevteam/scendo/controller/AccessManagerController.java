package com.scendodevteam.scendo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.scendodevteam.scendo.entity.TokenRegistrazione;
import com.scendodevteam.scendo.exception.GenericErrorException;
import com.scendodevteam.scendo.model.UtenteMD;
import com.scendodevteam.scendo.service.UtenteSC;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AccessManagerController{

    @Autowired
    private UtenteSC utenteSC;


    @PostMapping("/api/registrazione")
    public String registerUser(@Valid @RequestBody UtenteMD usr, HttpServletRequest request) throws GenericErrorException{

        TokenRegistrazione tokenRegistrazione = utenteSC.registerUser(usr);
        String url = "http://" + 
                    request.getServerName() + 
                    ":" + 
                    request.getServerPort() + 
                    request.getContextPath() + 
                    "/api/verifica-registrazione?token=" + 
                    tokenRegistrazione.getToken();
        
        return url;
    }

    @GetMapping("/api/verifica-registrazione") //localhost:8080/api/verifica-registrazione?token=tokenstring
    public String verifyRegistration(@RequestParam("token") String token) throws GenericErrorException{

        utenteSC.verifyRegistration(token);
        
        return "Account verificato";
    }

}
