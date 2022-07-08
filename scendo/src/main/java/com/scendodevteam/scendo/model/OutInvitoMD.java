package com.scendodevteam.scendo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutInvitoMD {

    private long idInvito;

    private String emailInvitante;
    
    private long idUscita;
}
