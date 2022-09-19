package com.scendodevteam.scendo.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.scendodevteam.scendo.model.EmailDetails;

@Service
@Profile("dev")
public class EmailSendSCDevImplementazione implements EmailSendSC{

    @Override
    public void sendSimpleMail(EmailDetails details) throws Exception{

        System.out.println("--- Nuovo messaggio email in uscita---"
                            + "\n\tFrom: \"" + details.getSender() + "\""
                            + "\n\tTo: \"" + details.getRecipient() + "\""
                            + "\n\tSubject: \"" + details.getSubject() + "\""
                            + "\n\tBody: \"" + details.getMsgBody() + "\"\n"
                            + "--- Fine del messaggio email ---\n");

    }
}