package com.scendodevteam.scendo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.scendodevteam.scendo.entity.TokenRegistrazione;
import com.scendodevteam.scendo.exception.GenericErrorException;
import com.scendodevteam.scendo.model.JwtRequest;
import com.scendodevteam.scendo.model.JwtResponse;
import com.scendodevteam.scendo.model.UtenteMD;
import com.scendodevteam.scendo.service.AuthUserService;
import com.scendodevteam.scendo.service.UtenteSC;
import com.scendodevteam.scendo.util.JwtUtil;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AccessManagerController{

    @Autowired
    private UtenteSC utenteSC;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthUserService authUserService;

    @PostMapping("/api/login")
    public JwtResponse login(@RequestBody JwtRequest jwtRequest) throws Exception{

            authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                jwtRequest.getUsername(), 
                jwtRequest.getPassword()));

        
        final UserDetails userDetails = authUserService.loadUserByUsername(jwtRequest.getUsername());
        
        final String token = jwtUtil.generateToken(userDetails);
        
        return new JwtResponse(token);

    }

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
