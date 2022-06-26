package com.scendodevteam.scendo.model;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UscitaMD {

    @NotEmpty(message = "Il tipo dell'uscita non può essere vuoto")
    private String tipoUscita;

    @Future(message = "La data e l'ora dell'ucita devono essere nel futuro")
    private Date dataEOra;
    
    @NotEmpty(message = "La location dell'uscita non può essere vuota")
    private String locationUscita;

    private String locationIncontro;

    @NotNull(message = "Devi specificare il tipo di uscita")
    private boolean uscitaPrivata;

    @NotNull(message = "Il numero massimo dei partecipanti non può essere nullo")
    @Min(value = 1, message = "Il numero minimo dei partecipanti deve essere 1")
    private int numeroMaxPartecipanti;

    private String descrizione;
    
}
