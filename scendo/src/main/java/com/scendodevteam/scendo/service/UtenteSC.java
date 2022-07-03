package com.scendodevteam.scendo.service;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.scendodevteam.scendo.entity.TokenRegistrazione;
import com.scendodevteam.scendo.exception.GenericErrorException;
import com.scendodevteam.scendo.model.UtenteMD;


public interface UtenteSC {

    public TokenRegistrazione registerUser(UtenteMD usr) throws GenericErrorException;

    public boolean verifyRegistration(@NotNull Optional<String> token) throws GenericErrorException;
    
}
