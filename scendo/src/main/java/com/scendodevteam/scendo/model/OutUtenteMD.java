package com.scendodevteam.scendo.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutUtenteMD {
    
    private String nome;

    private String cognome;

    private String email;

    private Date dataDiNascita = null;

    private Integer sesso = null;

    private String cittaDiResidenza = null;

    private String codicePostale = null;
    
}
