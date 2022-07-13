package com.scendodevteam.scendo.units.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.scendodevteam.scendo.entity.TokenRegistrazione;
import com.scendodevteam.scendo.entity.Utente;
import com.scendodevteam.scendo.repository.TokenRegistrazioneDB;

@DataJpaTest
public class TokenRegistrazioneDBTest {
    
    @Autowired
    private TokenRegistrazioneDB tokenRegistrazioneDB;

    @Autowired
    private TestEntityManager testEntityManager;

    private String token_corretto = UUID.randomUUID().toString();
    
    private String token_errato = UUID.randomUUID().toString();

    @BeforeEach
    void setUp() throws ParseException{
        
        Utente utente = new Utente();
        utente.setNome("Raffaele");
        utente.setCognome("del Gaudio");
        Date dataDiNascita = new SimpleDateFormat("dd/MM/yyyy").parse("24/10/1998");
        utente.setDataDiNascita(dataDiNascita);
        utente.setSesso(1);
        utente.setEmail("email@test.com");
        utente.setPassword("password");
        utente.setCittaDiResidenza("Napoli");
        utente.setCodicePostale("80124");
        utente.setActive(true);

        testEntityManager.persist(utente);

        TokenRegistrazione tokenRegistrazione = new TokenRegistrazione();
        Date dataScadenza = new SimpleDateFormat("dd/MM/yyyy").parse("24/10/2127");
        tokenRegistrazione.setDataScadenza(dataScadenza);
        tokenRegistrazione.setToken(token_corretto);
        tokenRegistrazione.setUtente(utente);

        testEntityManager.persist(tokenRegistrazione);

    }
    
    @Test
    void testFindByToken_quandoTokenEsiste() {

        TokenRegistrazione tokenRegistrazione = tokenRegistrazioneDB.findByToken(token_corretto);

        assertEquals(tokenRegistrazione.getToken(), token_corretto);

    }

    @Test
    void testFindByToken_quandoTokenNonEsiste() {

        TokenRegistrazione tokenRegistrazione = tokenRegistrazioneDB.findByToken(token_errato);

        assertNull(tokenRegistrazione);

    }
}
