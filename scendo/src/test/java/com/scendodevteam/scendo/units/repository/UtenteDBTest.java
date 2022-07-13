package com.scendodevteam.scendo.units.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.scendodevteam.scendo.entity.Utente;
import com.scendodevteam.scendo.repository.UtenteDB;

@DataJpaTest
public class UtenteDBTest {
    
    @Autowired
    private UtenteDB utenteDB;

    @Autowired
    private TestEntityManager testEntityManager;

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
        
    }
    
    @Test
    void testExistsByEmail_quandoUtenteEsiste() {

        assertTrue(utenteDB.existsByEmail("email@test.com"));

    }

    @Test
    void testExistsByEmail_quandoUtenteNonEsiste() {

        assertFalse(utenteDB.existsByEmail("emailerrata@test.com"));

    }

    @Test
    void testFindByEmail_quandoUtenteEsiste() {

        Utente utente = utenteDB.findByEmail("email@test.com");
        
        assertEquals(utente.getEmail(), "email@test.com");

    }

    @Test
    void testFindByEmail_quandoNonUtenteEsiste() {

        Utente utente = utenteDB.findByEmail("emailerrata@test.com");
        assertNull(utente);

    }
}
