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
@Table(name = "utenti")
public class Utente{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idUtente;

    private String nome;

    private String cognome;

    private Date dataDiNascita;

    private Integer sesso;

    private String email;

    private String password;

    private String cittaDiResidenza;

    private String codicePostale;

    private boolean active = false;

    @OneToMany(mappedBy = "utente")
    private Set<UtenteUscita> uscite;

    @OneToMany(mappedBy = "utenteInvitato", cascade = CascadeType.ALL)
    private List<Invito> invitiRicevuti;

    @OneToMany(mappedBy = "utenteInvitante", cascade = CascadeType.ALL)
    private List<Invito> invitiInviati;


}