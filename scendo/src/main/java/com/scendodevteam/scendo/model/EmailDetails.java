package com.scendodevteam.scendo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDetails {

    private String sender;
    
    private String recipient;

    private String msgBody;

    private String subject;

    private String attachment;

}
