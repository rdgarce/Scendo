package com.scendodevteam.scendo.entity;


import java.util.Date;
import java.util.List;

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

    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL)
    private List<UtenteUscita> uscite;

    @OneToMany(mappedBy = "utenteInvitato", cascade = CascadeType.ALL)
    private List<InvitoUscita> invitiUsciteRicevuti;

    @OneToMany(mappedBy = "utenteInvitante", cascade = CascadeType.ALL)
    private List<InvitoUscita> invitiUsciteInviati;

    @OneToOne(mappedBy = "utente")
    private TokenRegistrazione tokenRegistrazione;

    @OneToMany(mappedBy = "utenteInvitato", cascade = CascadeType.ALL)
    private List<InvitoAmicizia> invitiAmicizieRicevuti;

    @OneToMany(mappedBy = "utenteInvitante", cascade = CascadeType.ALL)
    private List<InvitoAmicizia> invitiAmicizieInviati;

    @OneToMany(mappedBy = "utente1", cascade = CascadeType.ALL)
    private List<Amicizia> AmicizieUtente1;

    @OneToMany(mappedBy = "utente2", cascade = CascadeType.ALL)
    private List<Amicizia> AmicizieUtente2;

}