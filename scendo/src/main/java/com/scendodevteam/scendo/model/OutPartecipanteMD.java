package com.scendodevteam.scendo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutPartecipanteMD {

    private String nome;

    private String cognome;

    private String email;

    private boolean utenteCreatore;
    
    private boolean utenteOrganizzatore;
    
}
