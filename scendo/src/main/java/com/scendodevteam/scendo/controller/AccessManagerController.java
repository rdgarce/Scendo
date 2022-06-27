package com.scendodevteam.scendo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.scendodevteam.scendo.config.JwtTokenUtil;
import com.scendodevteam.scendo.model.JwtRequest;
import com.scendodevteam.scendo.model.JwtResponse;
import com.scendodevteam.scendo.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.scendodevteam.scendo.entity.TokenRegistrazione;
import com.scendodevteam.scendo.exception.GenericError;
import com.scendodevteam.scendo.model.UtenteMD;
import com.scendodevteam.scendo.service.UtenteSC;

@RestController
@CrossOrigin
public class AccessManagerController{

    @Autowired
    private UtenteSC utenteSC;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = "/api/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/api/registrazione")
    public String registerUser(@Valid @RequestBody UtenteMD usr, HttpServletRequest request) throws GenericError{

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
    public String verifyRegistration(@RequestParam("token") String token) throws GenericError{

        utenteSC.verifyRegistration(token);
        
        return "Account verificato";
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
