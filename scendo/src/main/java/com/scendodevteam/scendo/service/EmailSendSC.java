package com.scendodevteam.scendo.service;

import com.scendodevteam.scendo.model.EmailDetails;

public interface EmailSendSC {
 
    public void sendSimpleMail(EmailDetails details) throws Exception;

}