package com.scendodevteam.scendo.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "uscite")
public class Uscita{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idUscita;

    private String tipoUscita;

    private Date dataEOra;

    private String locationUscita;

    private String locationIncontro;

    private boolean uscitaPrivata;

    private int numeroMaxPartecipanti;

    private String descrizione;

    @OneToMany(mappedBy = "uscita", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<UtenteUscita> utenti;

    @OneToMany(mappedBy = "uscita", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<InvitoUscita> inviti;


}