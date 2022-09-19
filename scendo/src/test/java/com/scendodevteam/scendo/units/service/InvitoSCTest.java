package com.scendodevteam.scendo.units.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.scendodevteam.scendo.entity.InvitoUscita;
import com.scendodevteam.scendo.entity.Uscita;
import com.scendodevteam.scendo.entity.Utente;
import com.scendodevteam.scendo.entity.UtenteUscita;
import com.scendodevteam.scendo.exception.GenericErrorException;
import com.scendodevteam.scendo.model.OutInvitoMD;
import com.scendodevteam.scendo.repository.InvitoDB;
import com.scendodevteam.scendo.repository.UscitaDB;
import com.scendodevteam.scendo.repository.UtenteDB;
import com.scendodevteam.scendo.repository.UtenteUscitaDB;
import com.scendodevteam.scendo.service.InvitoSC;

@SpringBootTest
public class InvitoSCTest {

    @Autowired
    private InvitoSC invitoSC;

    @MockBean
    private UtenteDB utenteDB;

    @MockBean
    private UscitaDB uscitaDB;

    @MockBean
    private UtenteUscitaDB utenteUscitaDB;

    @MockBean
    private InvitoDB invitoDB;

    private ArrayList<OutInvitoMD> outInviti = new ArrayList<OutInvitoMD>();

    private InvitoUscita invito_corretto;

    @BeforeEach
    void setUp() throws ParseException{

        Utente utente1 = new Utente();
        utente1.setIdUtente(1);
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

        Mockito.when(utenteDB.findByEmail("raffaele@test.com")).thenReturn(utente1);
        Mockito.when(utenteDB.existsByEmail("raffaele@test.com")).thenReturn(true);

        Uscita uscita1 = new Uscita();
        Date dataUscita1 = new SimpleDateFormat("dd/MM/yyyy").parse("30/01/2127");
        uscita1.setIdUscita(2);
        uscita1.setDataEOra(dataUscita1);
        uscita1.setDescrizione("descrizione");
        uscita1.setLocationIncontro("locationIncontro");
        uscita1.setLocationUscita("locationUscita");
        uscita1.setNumeroMaxPartecipanti(10);
        uscita1.setTipoUscita("tipoUscita");
        uscita1.setUscitaPrivata(true);

        Mockito.when(uscitaDB.existsById(2L)).thenReturn(true);
        Mockito.when(uscitaDB.getReferenceById(2L)).thenReturn(uscita1);

        ArrayList<UtenteUscita> utenti_partecipanti = new ArrayList<UtenteUscita>();
        
        UtenteUscita utenteUscita1 = new UtenteUscita();
        utenteUscita1.setIdUtenteUscita(3);
        utenteUscita1.setUscita(uscita1);
        utenteUscita1.setUtente(utente1);
        utenteUscita1.setUtenteCreatore(true);
        utenteUscita1.setUtenteOrganizzatore(false);

        utenti_partecipanti.add(utenteUscita1);

        Mockito.when(utenteUscitaDB.findByUscita(uscita1)).thenReturn(utenti_partecipanti);

        Utente utente2 = new Utente();
        utente2.setIdUtente(4);
        utente2.setNome("Simone");
        utente2.setCognome("D'Orta");
        Date dataDiNascita2 = new SimpleDateFormat("dd/MM/yyyy").parse("24/10/1998");
        utente2.setDataDiNascita(dataDiNascita2);
        utente2.setSesso(1);
        utente2.setEmail("simone@test.com");
        utente2.setPassword("password");
        utente2.setCittaDiResidenza("Napoli");
        utente2.setCodicePostale("80124");
        utente2.setActive(true);

        Mockito.when(utenteDB.findByEmail("simone@test.com")).thenReturn(utente2);
        Mockito.when(utenteDB.existsByEmail("simone@test.com")).thenReturn(true);

        ArrayList<InvitoUscita> inviti = new ArrayList<InvitoUscita>();

        InvitoUscita invito = new InvitoUscita();
        invito.setIdInvito(5);
        invito.setUscita(uscita1);
        invito.setUtenteInvitante(utente1);
        invito.setUtenteInvitato(utente2);

        invito_corretto = invito;

        inviti.add(invito);

        OutInvitoMD outInvitoMD = new OutInvitoMD();
        outInvitoMD.setIdInvito(invito.getIdInvito());
        outInvitoMD.setEmailInvitante(invito.getUtenteInvitante().getEmail());
        outInvitoMD.setIdUscita(invito.getUscita().getIdUscita());

        outInviti.add(outInvitoMD);

        Mockito.when(invitoDB.findByUscitaAndUtenteInvitato(uscita1, utente2)).thenReturn(inviti);

        Mockito.when(invitoDB.findByUtenteInvitato(utente2)).thenReturn(inviti);

        Mockito.when(utenteUscitaDB.findByUtenteAndUscita(utente2, uscita1)).thenReturn(new ArrayList<UtenteUscita>());
        Mockito.when(utenteUscitaDB.findByUtenteAndUscita(utente1, uscita1)).thenReturn(utenti_partecipanti);

        Mockito.when(invitoDB.save(any())).thenReturn(invito);


    }

    @Test
    void testAccettaInvito_quandoInvitoEsiste() {

        try {

            assertEquals("Invito accettato", invitoSC.accettaInvito("simone@test.com", 2L));

        } catch (Exception e) {

            fail("Lanciata eccezione: "+ e.getMessage());

        }

    }

    @Test
    void testAccettaInvito_quandoInvitoNonEsiste() {

        try {

            invitoSC.accettaInvito("raffaele@test.com", 2L);
            fail("Eccezione non lanciata");

        } catch (Exception e) {

            assertTrue(e instanceof GenericErrorException && ((GenericErrorException)e).getCode() == "AV_004");
        }

    }

    @Test
    void testLeggiInviti_quandoInvitiPresenti() {

        try {

            List<OutInvitoMD> inviti = invitoSC.leggiInviti("simone@test.com");

            assertTrue(inviti.size() == outInviti.size() && inviti.containsAll(outInviti) && outInviti.containsAll(inviti));

        } catch (Exception e) {

            fail("Lanciata eccezione: "+ e.getMessage());

        }

    }

    @Test
    void testRifiutaInvito_quandoInvitoEsiste() {

        try {

            assertEquals("Invito rifiutato",invitoSC.rifiutaInvito("simone@test.com", 2L));

        } catch (Exception e) {
            
            fail("Lanciata eccezione: "+ e.getMessage());

        }

    }

    @Test
    void testRifiutaInvito_quandoInvitoNonEsiste() {

        try {

            invitoSC.rifiutaInvito("raffaele@test.com", 2L);
            fail("Eccezione non lanciata");

        } catch (Exception e) {
            
            assertTrue(e instanceof GenericErrorException && ((GenericErrorException)e).getCode() == "RV_003");

        }

    }

    @Test
    void testSalvaInvito_quandoEsitoPositivo() {

        Mockito.when(invitoDB.findByUscitaAndUtenteInvitato(any(), any())).thenReturn(new ArrayList<InvitoUscita>());

        try {

            InvitoUscita invito_test = invitoSC.salvaInvito("raffaele@test.com", "simone@test.com", 2L);
            assertEquals(invito_corretto, invito_test);

        } catch (Exception e) {
            
            fail("Lanciata eccezione: "+ e.getMessage());
        
        }

    }
}
