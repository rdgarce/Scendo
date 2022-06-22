package com.scendodevteam.scendo.entity;

import java.util.Date;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TokenRegistrazione {
    
    //Tempo in minuti
    private static final int EXPIRATION_TIME = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idToken;
    private String token;
    private Date dataScadenza;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_utente",
    nullable = false,
    foreignKey = @ForeignKey(name="FK_TOKENREGISTRAZIONE_UTENTE"))
    private Utente utente;

    public TokenRegistrazione(Utente utente, String token) {
        super();
        this.token = token;
        this.utente = utente;
        this.dataScadenza = calcolaDataScadenza(EXPIRATION_TIME);
    }

    public TokenRegistrazione(String token) {
        super();
        this.token = token;
        this.dataScadenza = calcolaDataScadenza(EXPIRATION_TIME);
    }

    private Date calcolaDataScadenza(int expirationTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expirationTime);
        return new Date(calendar.getTime().getTime());
    }

    public boolean scaduto(){

        return this.dataScadenza.getTime() - Calendar.getInstance().getTime().getTime() <= 0;
    }

}
