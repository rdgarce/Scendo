package com.scendodevteam.scendo.event.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import com.scendodevteam.scendo.event.InvioTokenPasswordDimenticata;
import com.scendodevteam.scendo.model.EmailDetails;
import com.scendodevteam.scendo.service.EmailSendSC;

@EnableAsync
@Component
public class InvioTokenPasswordDimenticataListener implements ApplicationListener<InvioTokenPasswordDimenticata> {
    
    @Autowired
    private EmailSendSC emailSendSC;

    @Value("${scendo.website}")
    private String website;

    @Value("${scendo.email}")
    private String sender;

    @Async
    @Override
    public void onApplicationEvent(InvioTokenPasswordDimenticata event) {
        
        String url = "https://" + 
                    website + 
                    "/password-reset?token=" + 
                    event.getTokenPasswordDimenticata().getToken();
        

        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setSender(sender);
        emailDetails.setRecipient(event.getTokenPasswordDimenticata().getUtente().getEmail());
        emailDetails.setSubject("Reimposta la tua password");
        emailDetails.setMsgBody("Ciao dal team di Scendo.\nPer aggiornare la tua password clicca sul seguente link: "+ url);

        try {
            
            emailSendSC.sendSimpleMail(emailDetails);
        
        } catch (Exception e) {
            
            System.out.println("Invio dell'email da implementare, ecco il link per aggiornare la password:\n" + url);
            System.out.println(e.getMessage());
        }
    
    }
}
