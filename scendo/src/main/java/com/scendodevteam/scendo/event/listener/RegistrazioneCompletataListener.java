package com.scendodevteam.scendo.event.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import com.scendodevteam.scendo.event.RegistrazioneCompletata;
import com.scendodevteam.scendo.model.EmailDetails;
import com.scendodevteam.scendo.service.EmailSendSC;

@EnableAsync
@Component
public class RegistrazioneCompletataListener implements ApplicationListener<RegistrazioneCompletata> {
    
    @Autowired
    EmailSendSC emailSendSC;

    @Async
    @Override
    public void onApplicationEvent(RegistrazioneCompletata event) {
        
        String url = "http://" + 
                    event.getRequest().getServerName() + 
                    ":" + 
                    event.getRequest().getServerPort() + 
                    event.getRequest().getContextPath() + 
                    "/api/verifica-registrazione?token=" + 
                    event.getTokenRegistrazione().getToken();
        

        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(event.getTokenRegistrazione().getUtente().getEmail());
        emailDetails.setSubject("Verifica il tuo account");
        emailDetails.setMsgBody("Ciao dal team di Scendo.\nPer verificare il tuo account clicca sul seguente link: "+ url);

        try {
            
            emailSendSC.sendSimpleMail(emailDetails);
        
        } catch (Exception e) {
            
            System.out.println("Invio dell'email da implementare, ecco il link di verifica:\n" + url);
            System.out.println(e.getMessage());
        }
    
    }
}
