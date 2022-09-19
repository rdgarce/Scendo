package com.scendodevteam.scendo.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.scendodevteam.scendo.entity.TokenPasswordDimenticata;
import com.scendodevteam.scendo.entity.TokenRegistrazione;
import com.scendodevteam.scendo.event.InvioTokenPasswordDimenticata;
import com.scendodevteam.scendo.event.InvioTokenRegistrazione;
import com.scendodevteam.scendo.exception.GenericErrorException;
import com.scendodevteam.scendo.model.JwtRequest;
import com.scendodevteam.scendo.model.MessaggioGenerico;
import com.scendodevteam.scendo.model.InPasswordResetMD;
import com.scendodevteam.scendo.model.InUtenteMD;
import com.scendodevteam.scendo.service.AuthUserSC;
import com.scendodevteam.scendo.service.UtenteSC;
import com.scendodevteam.scendo.util.JwtUtil;

@RestController
@CrossOrigin(origins = {"http://scendo.it","https://scendo.it","http://www.scendo.it","https://www.scendo.it"})
public class AccessManagerController{

    @Autowired
    private UtenteSC utenteSC;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthUserSC authUserService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping("/api/login")
    public MessaggioGenerico login(@RequestBody JwtRequest jwtRequest) throws Exception{

            authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                jwtRequest.getUsername(), 
                jwtRequest.getPassword()));

        
        final UserDetails userDetails = authUserService.loadUserByUsername(jwtRequest.getUsername());
        
        final String token = jwtUtil.generateToken(userDetails);
        
        return new MessaggioGenerico(token,"LG_000");

    }

    @PostMapping("/api/registrazione")
    public MessaggioGenerico registerUser(@Valid @RequestBody InUtenteMD usr) throws GenericErrorException{

        TokenRegistrazione tokenRegistrazione = utenteSC.registerUser(usr);
        
        publisher.publishEvent(new InvioTokenRegistrazione(tokenRegistrazione));
        
        return new MessaggioGenerico("Registrazione completata con successo. Clicca sul link che riceverai per email per attivare il tuo account.","RG_000");
    }

    @GetMapping("/api/verifica-registrazione") //localhost:8080/api/verifica-registrazione?token=tokenstring
    public MessaggioGenerico verifyRegistration(@RequestParam("token") Optional<String> token) throws GenericErrorException{

        utenteSC.verifyRegistration(token);
        
        return new MessaggioGenerico("Account verificato","VR_000");
    }

    @GetMapping("/api/reinvia-token") //localhost:8080/api/reinvia-token?email=email@email.com
    public MessaggioGenerico resendToken(@RequestParam Optional<String> email) throws GenericErrorException{

        TokenRegistrazione tokenRegistrazione = utenteSC.resendToken(email);

        publisher.publishEvent(new InvioTokenRegistrazione(tokenRegistrazione));
        
        return new MessaggioGenerico("L'email contenente il link di verifica è stata reinviata","RT_000");
    }

    @GetMapping("/api/password-dimenticata") //localhost:8080/api/password-dimenticata?email=email@email.com
    public MessaggioGenerico passwordForgotten(@RequestParam Optional<String> email) throws GenericErrorException{

        TokenPasswordDimenticata tokenPasswordDimenticata = utenteSC.passwordForgotten(email);

        publisher.publishEvent(new InvioTokenPasswordDimenticata(tokenPasswordDimenticata));

        return new MessaggioGenerico("Ti è stata inviata una email con il procedimento da seguire per cambiare la tua password","PD_000");

    }

    @PostMapping("/api/password-reset")
    public MessaggioGenerico passwordReset(@Valid @RequestBody InPasswordResetMD inPasswordResetMD) throws GenericErrorException{

        utenteSC.passwordReset(inPasswordResetMD);

        return new MessaggioGenerico("La tua password è stata cambiata con successo","PR_000");


    }

}
