package com.scendodevteam.scendo.entity;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


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

}