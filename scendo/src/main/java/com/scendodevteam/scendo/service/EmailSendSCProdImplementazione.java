package com.scendodevteam.scendo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.scendodevteam.scendo.model.EmailDetails;

@Service
@Profile("prod")
public class EmailSendSCProdImplementazione implements EmailSendSC{


    @Autowired 
    private JavaMailSender javaMailSender;

    @Override
    public void sendSimpleMail(EmailDetails details) throws Exception{
        
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(details.getSender());
        mailMessage.setTo(details.getRecipient());
        mailMessage.setText(details.getMsgBody());
        mailMessage.setSubject(details.getSubject());

        javaMailSender.send(mailMessage);

    }
}