package com.scendodevteam.scendo.units.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.scendodevteam.scendo.entity.Invito;
import com.scendodevteam.scendo.entity.Uscita;
import com.scendodevteam.scendo.entity.Utente;
import com.scendodevteam.scendo.repository.InvitoDB;

@DataJpaTest
public class InvitoDBTest {

    @Autowired
    private InvitoDB invitoDB;

    @Autowired
    private TestEntityManager testEntityManager;

    private Invito invito_db;

    private Utente utenteInvitato_db_corretto;

    private Utente utenteInvitato_db_errato;

    private Uscita uscita_db_corretta;

    private Uscita uscita_db_errata;


    @BeforeEach
    void setUp() throws ParseException{

        Utente utenteInvitante = new Utente();
        utenteInvitante.setNome("Raffaele");
        utenteInvitante.setCognome("del Gaudio");
        Date dataDiNascita1 = new SimpleDateFormat("dd/MM/yyyy").parse("24/10/1998");
        utenteInvitante.setDataDiNascita(dataDiNascita1);
        utenteInvitante.setSesso(1);
        utenteInvitante.setEmail("raffaele@test.com");
        utenteInvitante.setPassword("password");
        utenteInvitante.setCittaDiResidenza("Napoli");
        utenteInvitante.setCodicePostale("80124");
        utenteInvitante.setActive(true);

        Utente utente1 = new Utente();
        utente1.setNome("Simone");
        utente1.setCognome("D'Orta");
        Date dataDiNascita2 = new SimpleDateFormat("dd/MM/yyyy").parse("24/01/1999");
        utente1.setDataDiNascita(dataDiNascita2);
        utente1.setSesso(1);
        utente1.setEmail("simone@test.com");
        utente1.setPassword("password");
        utente1.setCittaDiResidenza("Napoli");
        utente1.setCodicePostale("80124");
        utente1.setActive(true);

        Utente utente2 = new Utente();
        utente2.setNome("Fantasma");
        utente2.setCognome("Formaggino");
        Date dataDiNascita4 = new SimpleDateFormat("dd/MM/yyyy").parse("09/09/1998");
        utente2.setDataDiNascita(dataDiNascita4);
        utente2.setSesso(1);
        utente2.setEmail("daniele@test.com");
        utente2.setPassword("password");
        utente2.setCittaDiResidenza("Napoli");
        utente2.setCodicePostale("80124");
        utente2.setActive(true);

        testEntityManager.persist(utenteInvitante);
        utenteInvitato_db_corretto = testEntityManager.persist(utente1);
        utenteInvitato_db_errato = testEntityManager.persist(utente2);

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

        uscita_db_corretta = testEntityManager.persist(uscita1);
        uscita_db_errata = testEntityManager.persist(uscita2);

        Invito invito = new Invito();
        invito.setUscita(uscita1);
        invito.setUtenteInvitante(utenteInvitante);
        invito.setUtenteInvitato(utente1);

        invito_db = testEntityManager.persist(invito);
        
    }


    @Test
    void testFindByUtenteInvitato_quandoUtenteInvitatoEsiste() {

        List<Invito> inviti_list = invitoDB.findByUtenteInvitato(utenteInvitato_db_corretto);

        assertTrue(inviti_list.size() == 1 && inviti_list.contains(invito_db));

    }

    @Test
    void testFindByUtenteInvitato_quandoUtenteInvitatoNonEsiste() {

        List<Invito> inviti_list = invitoDB.findByUtenteInvitato(utenteInvitato_db_errato);

        assertTrue(inviti_list != null && inviti_list.size() == 0);

    }

    @Test
    void testFindByUscitaAndUtenteInvitato_quandoUtenteInvitatoEUscitaEsistono() {

        List<Invito> inviti_list = invitoDB.findByUscitaAndUtenteInvitato(uscita_db_corretta, utenteInvitato_db_corretto);

        assertTrue(inviti_list.size() == 1 && inviti_list.contains(invito_db));

    }

    @Test
    void testFindByUscitaAndUtenteInvitato_quandoUtenteInvitatoEsisteEUscitaNonEsiste() {

        List<Invito> inviti_list = invitoDB.findByUscitaAndUtenteInvitato(uscita_db_errata, utenteInvitato_db_corretto);

        assertTrue(inviti_list != null && inviti_list.size() == 0);

    }

    @Test
    void testFindByUscitaAndUtenteInvitato_quandoUtenteInvitatoNonEsisteEUscitaEsiste() {

        List<Invito> inviti_list = invitoDB.findByUscitaAndUtenteInvitato(uscita_db_corretta, utenteInvitato_db_errato);

        assertTrue(inviti_list != null && inviti_list.size() == 0);

    }

    @Test
    void testFindByUscitaAndUtenteInvitato_quandoUtenteInvitatoEUscitaNonEsistono() {

        List<Invito> inviti_list = invitoDB.findByUscitaAndUtenteInvitato(uscita_db_errata, utenteInvitato_db_errato);

        assertTrue(inviti_list != null && inviti_list.size() == 0);

    }
    
}
