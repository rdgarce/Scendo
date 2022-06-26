package com.scendodevteam.scendo.service;
import com.scendodevteam.scendo.entity.TokenRegistrazione;
import com.scendodevteam.scendo.exception.GenericError;
import com.scendodevteam.scendo.model.UtenteMD;


public interface UtenteSC {

    public TokenRegistrazione registerUser(UtenteMD usr) throws GenericError;

    public boolean verifyRegistration(String token);
    
}
