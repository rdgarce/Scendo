package com.scendodevteam.scendo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.scendodevteam.scendo.model.EmailDetails;

@Service
public class EmailSendSCImplementazione implements EmailSendSC{


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