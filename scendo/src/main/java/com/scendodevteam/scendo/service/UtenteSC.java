package com.scendodevteam.scendo.service;
import java.util.Optional;

import com.scendodevteam.scendo.entity.TokenRegistrazione;
import com.scendodevteam.scendo.exception.GenericErrorException;
import com.scendodevteam.scendo.model.InUtenteMD;
import com.scendodevteam.scendo.model.OutUtenteMD;


public interface UtenteSC {

    public TokenRegistrazione registerUser(InUtenteMD usr) throws GenericErrorException;

    public boolean verifyRegistration(Optional<String> token) throws GenericErrorException;
    
    public OutUtenteMD infoUtente(String email) throws GenericErrorException;

    public OutUtenteMD mieInfo(String username);
    
}
