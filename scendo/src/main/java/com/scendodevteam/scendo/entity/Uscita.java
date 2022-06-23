package com.scendodevteam.scendo.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

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

    private int numeroPartecipanti;

    private String descrizione;

    @OneToMany(mappedBy = "uscita")
    private Set<UtenteUscita> utenti;

    @OneToMany(mappedBy = "uscita", cascade = CascadeType.ALL)
    private List<Invito> inviti;


}