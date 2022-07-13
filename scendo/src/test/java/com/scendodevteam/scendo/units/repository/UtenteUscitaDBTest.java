package com.scendodevteam.scendo.units.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.scendodevteam.scendo.entity.Uscita;
import com.scendodevteam.scendo.entity.Utente;
import com.scendodevteam.scendo.entity.UtenteUscita;
import com.scendodevteam.scendo.repository.UtenteUscitaDB;

@DataJpaTest
public class UtenteUscitaDBTest {
    
    @Autowired
    private UtenteUscitaDB utenteUscitaDB;

    @Autowired
    private TestEntityManager testEntityManager;

    private Uscita uscita_db_corretta;
    
    private Utente utente_db_errato;

    private Uscita uscita_db_errata;

    private ArrayList<Utente> utenti_db = new ArrayList<Utente>();

    private ArrayList<UtenteUscita> utentiUscite_db = new ArrayList<UtenteUscita>();


    @BeforeEach
    void setUp() throws ParseException{

        Utente utente1 = new Utente();
        utente1.setNome("Raffaele");
        utente1.setCognome("del Gaudio");
        Date dataDiNascita1 = new SimpleDateFormat("dd/MM/yyyy").parse("24/10/1998");
        utente1.setDataDiNascita(dataDiNascita1);
        utente1.setSesso(1);
        utente1.setEmail("raffaele@test.com");
        utente1.setPassword("password");
        utente1.setCittaDiResidenza("Napoli");
        utente1.setCodicePostale("80124");
        utente1.setActive(true);

        Utente utente2 = new Utente();
        utente2.setNome("Simone");
        utente2.setCognome("D'Orta");
        Date dataDiNascita2 = new SimpleDateFormat("dd/MM/yyyy").parse("24/01/1999");
        utente2.setDataDiNascita(dataDiNascita2);
        utente2.setSesso(1);
        utente2.setEmail("simone@test.com");
        utente2.setPassword("password");
        utente2.setCittaDiResidenza("Napoli");
        utente2.setCodicePostale("80124");
        utente2.setActive(true);

        Utente utente3 = new Utente();
        utente3.setNome("Daniele");
        utente3.setCognome("Marfella");
        Date dataDiNascita3 = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/1998");
        utente3.setDataDiNascita(dataDiNascita3);
        utente3.setSesso(1);
        utente3.setEmail("daniele@test.com");
        utente3.setPassword("password");
        utente3.setCittaDiResidenza("Napoli");
        utente3.setCodicePostale("80124");
        utente3.setActive(true);

        Utente utente4 = new Utente();
        utente4.setNome("Fantasma");
        utente4.setCognome("Formaggino");
        Date dataDiNascita4 = new SimpleDateFormat("dd/MM/yyyy").parse("09/09/1998");
        utente4.setDataDiNascita(dataDiNascita4);
        utente4.setSesso(1);
        utente4.setEmail("daniele@test.com");
        utente4.setPassword("password");
        utente4.setCittaDiResidenza("Napoli");
        utente4.setCodicePostale("80124");
        utente4.setActive(true);

        Uscita uscita1 = new Uscita();
        Date dataUscita1 = new SimpleDateFormat("dd/MM/yyyy").parse("30/01/2127");
        uscita1.setDataEOra(dataUscita1);
        uscita1.setDescrizione("descrizione");
        uscita1.setLocationIncontro("locationIncontro");
        uscita1.setLocationUscita("locationUscita");
        uscita1.setNumeroMaxPartecipanti(10);
        uscita1.setTipoUscita("tipoUscita");
        uscita1.setUscitaPrivata(true);

        Uscita uscita2 = new Uscita();
        Date dataUscita2 = new SimpleDateFormat("dd/MM/yyyy").parse("30/01/2127");
        uscita2.setDataEOra(dataUscita2);
        uscita2.setDescrizione("descrizione");
        uscita2.setLocationIncontro("locationIncontro");
        uscita2.setLocationUscita("locationUscita");
        uscita2.setNumeroMaxPartecipanti(10);
        uscita2.setTipoUscita("tipoUscita");
        uscita2.setUscitaPrivata(true);
        
        utenti_db.add(testEntityManager.persist(utente1));
        utenti_db.add(testEntityManager.persist(utente2));
        utenti_db.add(testEntityManager.persist(utente3));

        utente_db_errato = testEntityManager.persist(utente4);

        uscita_db_corretta = testEntityManager.persist(uscita1);
        uscita_db_errata = testEntityManager.persist(uscita2);

        UtenteUscita utenteUscita1 = new UtenteUscita();
        utenteUscita1.setUscita(uscita1);
        utenteUscita1.setUtente(utente1);
        utenteUscita1.setUtenteCreatore(true);
        utenteUscita1.setUtenteOrganizzatore(false);

        UtenteUscita utenteUscita2 = new UtenteUscita();
        utenteUscita2.setUscita(uscita1);
        utenteUscita2.setUtente(utente2);
        utenteUscita2.setUtenteCreatore(false);
        utenteUscita2.setUtenteOrganizzatore(true);

        UtenteUscita utenteUscita3 = new UtenteUscita();
        utenteUscita3.setUscita(uscita1);
        utenteUscita3.setUtente(utente3);
        utenteUscita3.setUtenteCreatore(false);
        utenteUscita3.setUtenteOrganizzatore(false);

        utentiUscite_db.add(testEntityManager.persist(utenteUscita1));
        utentiUscite_db.add(testEntityManager.persist(utenteUscita2));
        utentiUscite_db.add(testEntityManager.persist(utenteUscita3));

    }

    @Test
    void testFindByUscita_quandoUscitaEsiste() {

        List<UtenteUscita> utenteUscita_list = utenteUscitaDB.findByUscita(uscita_db_corretta);
        
        assertTrue(utenteUscita_list.size() == utentiUscite_db.size() && utenteUscita_list.containsAll(utentiUscite_db) && utentiUscite_db.containsAll(utenteUscita_list));

    }

    @Test
    void testFindByUscita_quandoUscitaNonEsiste() {

        List<UtenteUscita> utenteUscita_list = utenteUscitaDB.findByUscita(uscita_db_errata);
        
        assertFalse(utentiUscite_db.containsAll(utenteUscita_list) && utenteUscita_list.containsAll(utentiUscite_db));

    }

    @Test
    void testFindByUtente_quandoUtenteEsiste() {

        List<UtenteUscita> utenteUscita_list = utenteUscitaDB.findByUtente(utenti_db.get(0));
        
        assertTrue(utenteUscita_list.size() == 1 && utenteUscita_list.get(0).getUscita().getIdUscita() == uscita_db_corretta.getIdUscita());

    }

    @Test
    void testFindByUtente_quandoUtenteNonEsiste() {

        List<UtenteUscita> utenteUscita_list = utenteUscitaDB.findByUtente(utente_db_errato);
        
        assertFalse(utentiUscite_db.containsAll(utenteUscita_list) && utenteUscita_list.containsAll(utentiUscite_db));

    }

    @Test
    void testFindByUtenteAndUscita_quandoUtenteEUscitaEsistono() {

        List<UtenteUscita> utenteUscita_list = utenteUscitaDB.findByUtenteAndUscita(utenti_db.get(0),uscita_db_corretta);

        assertTrue(utenteUscita_list.size() == 1 && utentiUscite_db.contains(utenteUscita_list.get(0)));

    }

    @Test
    void testFindByUtenteAndUscita_quandoUtenteEsisteEUscitaNonEsiste() {

        List<UtenteUscita> utenteUscita_list = utenteUscitaDB.findByUtenteAndUscita(utenti_db.get(0),uscita_db_errata);

        assertTrue(utenteUscita_list != null && utenteUscita_list.size() == 0);

    }

    @Test
    void testFindByUtenteAndUscita_quandoUtenteNonEsisteEUscitaEsiste() {

        List<UtenteUscita> utenteUscita_list = utenteUscitaDB.findByUtenteAndUscita(utente_db_errato,uscita_db_corretta);

        assertTrue(utenteUscita_list != null && utenteUscita_list.size() == 0);

    }

    @Test
    void testFindByUtenteAndUscita_quandoUtenteEUscitaNonEsistono() {

        List<UtenteUscita> utenteUscita_list = utenteUscitaDB.findByUtenteAndUscita(utente_db_errato,uscita_db_errata);

        assertTrue(utenteUscita_list != null && utenteUscita_list.size() == 0);

    }

}