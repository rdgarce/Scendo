package com.scendodevteam.scendo.model;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutUscitaMD {

    private long idUscita;

    private String tipoUscita;

    private Date dataEOra;

    private String locationUscita;

    private String locationIncontro;

    private boolean uscitaPrivata;

    private int numeroMaxPartecipanti;

    private String descrizione;

    private List<OutPartecipanteMD> partecipanti = null;
    
}
