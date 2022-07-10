package com.scendodevteam.scendo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.scendodevteam.scendo.entity.Invito;
import com.scendodevteam.scendo.entity.Uscita;
import com.scendodevteam.scendo.entity.Utente;
import com.scendodevteam.scendo.entity.UtenteUscita;
import com.scendodevteam.scendo.model.InUscitaMD;
import com.scendodevteam.scendo.model.OutPartecipanteMD;
import com.scendodevteam.scendo.model.OutUscitaMD;
import com.scendodevteam.scendo.repository.InvitoDB;
import com.scendodevteam.scendo.repository.UscitaDB;
import com.scendodevteam.scendo.repository.UtenteDB;
import com.scendodevteam.scendo.repository.UtenteUscitaDB;

@SpringBootTest
public class UscitaSCTest {

    @Autowired
    private UscitaSC uscitaSC;

    @MockBean
    private UtenteDB utenteDB;

    @MockBean
    private UtenteUscitaDB utenteUscitaDB;

    @MockBean
    private UscitaDB uscitaDB;

    @MockBean
    private InvitoDB invitoDB;

    private ArrayList<Long> uscite = new ArrayList<Long>();

    private InUscitaMD inUscitaMD_corretta = new InUscitaMD();

    private OutUscitaMD outUscitaMD_corretta = new OutUscitaMD();

    private ArrayList<OutPartecipanteMD> outPartecipanteMD_list = new ArrayList<OutPartecipanteMD>();

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

        OutPartecipanteMD outPartecipanteMD1 = new OutPartecipanteMD();
        outPartecipanteMD1.setCognome(utente1.getCognome());
        outPartecipanteMD1.setEmail(utente1.getEmail());
        outPartecipanteMD1.setNome(utente1.getNome());
        outPartecipanteMD1.setUtenteCreatore(true);
        outPartecipanteMD1.setUtenteOrganizzatore(false);

        Utente utente2 = new Utente();
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

        OutPartecipanteMD outPartecipanteMD2 = new OutPartecipanteMD();
        outPartecipanteMD2.setCognome(utente2.getCognome());
        outPartecipanteMD2.setEmail(utente2.getEmail());
        outPartecipanteMD2.setNome(utente2.getNome());
        outPartecipanteMD2.setUtenteCreatore(false);
        outPartecipanteMD2.setUtenteOrganizzatore(false);

        Uscita uscita1 = new Uscita();
        Date dataUscita1 = new SimpleDateFormat("dd/MM/yyyy").parse("30/01/2127");
        uscita1.setIdUscita(1);
        uscita1.setDataEOra(dataUscita1);
        uscita1.setDescrizione("descrizione");
        uscita1.setLocationIncontro("locationIncontro");
        uscita1.setLocationUscita("locationUscita");
        uscita1.setNumeroMaxPartecipanti(10);
        uscita1.setTipoUscita("tipoUscita");
        uscita1.setUscitaPrivata(true);

        outUscitaMD_corretta.setIdUscita(uscita1.getIdUscita());
        outUscitaMD_corretta.setDataEOra(uscita1.getDataEOra());
        outUscitaMD_corretta.setDescrizione(uscita1.getDescrizione());
        outUscitaMD_corretta.setLocationIncontro(uscita1.getLocationIncontro());
        outUscitaMD_corretta.setLocationUscita(uscita1.getLocationUscita());
        outUscitaMD_corretta.setNumeroMaxPartecipanti(uscita1.getNumeroMaxPartecipanti());
        outUscitaMD_corretta.setTipoUscita(uscita1.getTipoUscita());
        outUscitaMD_corretta.setUscitaPrivata(uscita1.isUscitaPrivata());
        
        outPartecipanteMD_list.add(outPartecipanteMD1);
        outPartecipanteMD_list.add(outPartecipanteMD2);

        inUscitaMD_corretta.setDataEOra(uscita1.getDataEOra());
        inUscitaMD_corretta.setDescrizione(uscita1.getDescrizione());
        inUscitaMD_corretta.setLocationIncontro(uscita1.getLocationIncontro());
        inUscitaMD_corretta.setLocationUscita(uscita1.getLocationUscita());
        inUscitaMD_corretta.setNumeroMaxPartecipanti(uscita1.getNumeroMaxPartecipanti());
        inUscitaMD_corretta.setTipoUscita(uscita1.getTipoUscita());
        inUscitaMD_corretta.setUscitaPrivata(uscita1.isUscitaPrivata());

        Uscita uscita2 = new Uscita();
        Date dataUscita2 = new SimpleDateFormat("dd/MM/yyyy").parse("30/01/2127");
        uscita2.setIdUscita(2);
        uscita2.setDataEOra(dataUscita2);
        uscita2.setDescrizione("descrizione");
        uscita2.setLocationIncontro("locationIncontro");
        uscita2.setLocationUscita("locationUscita");
        uscita2.setNumeroMaxPartecipanti(10);
        uscita2.setTipoUscita("tipoUscita");
        uscita2.setUscitaPrivata(true);

        UtenteUscita utenteUscita1 = new UtenteUscita();
        utenteUscita1.setIdUtenteUscita(3);
        utenteUscita1.setUscita(uscita1);
        utenteUscita1.setUtente(utente1);
        utenteUscita1.setUtenteCreatore(true);

        UtenteUscita utenteUscita2 = new UtenteUscita();
        utenteUscita2.setIdUtenteUscita(3);
        utenteUscita2.setUscita(uscita1);
        utenteUscita2.setUtente(utente2);

        ArrayList<UtenteUscita> list = new ArrayList<UtenteUscita>();
        list.add(utenteUscita1);
        uscite.add(utenteUscita1.getUscita().getIdUscita());

        ArrayList<UtenteUscita> utente1_list = new ArrayList<UtenteUscita>();
        utente1_list.add(utenteUscita1);

        ArrayList<UtenteUscita> utente2_list = new ArrayList<UtenteUscita>();
        utente2_list.add(utenteUscita2);

        ArrayList<UtenteUscita> partecipanti = new ArrayList<UtenteUscita>();
        partecipanti.add(utenteUscita1);
        partecipanti.add(utenteUscita2);

        Mockito.when(utenteDB.findByEmail("raffaele@test.com")).thenReturn(utente1);
        Mockito.when(utenteDB.findByEmail("simone@test.com")).thenReturn(utente2);

        Mockito.when(utenteUscitaDB.findByUtente(utente1)).thenReturn(list);
        Mockito.when(utenteUscitaDB.findByUtente(utente2)).thenReturn(new ArrayList<UtenteUscita>());

        Mockito.when(uscitaDB.findById(1L)).thenReturn(Optional.of(uscita1));

        Mockito.when(utenteUscitaDB.findByUtenteAndUscita(utente1,uscita1)).thenReturn(utente1_list);
        Mockito.when(utenteUscitaDB.findByUtenteAndUscita(utente2,uscita1)).thenReturn(utente2_list);

        Mockito.when(invitoDB.findByUscitaAndUtenteInvitato(uscita1, utente1)).thenReturn(new ArrayList<Invito>());

        Mockito.when(utenteUscitaDB.findByUscita(uscita1)).thenReturn(partecipanti);

    }

    @Test
    void testConsultaCalendario_quandoUtenteEsiste() {

        try {

            List<Long> uscite_list = uscitaSC.consultaCalendario("raffaele@test.com");
            assertTrue(uscite_list.size() == uscite.size() && uscite_list.containsAll(uscite) && uscite.containsAll(uscite_list));
        
        } catch (Exception e) {
            
            fail("Lanciata eccezione: "+ e.getMessage());

        }
    
    }

    @Test
    void testConsultaCalendario_quandoUtenteNonEsiste() {

        try {

            List<Long> uscite_list = uscitaSC.consultaCalendario("simone@test.com");
            assertTrue(uscite_list.isEmpty());

        } catch (Exception e) {
            
            fail("Lanciata eccezione: "+ e.getMessage());

        }
    
    }

    @Test
    void testCreaUscita_quandoEsitoPositivo() {

        try {

            uscitaSC.creaUscita("raffaele@test.com", inUscitaMD_corretta);


        } catch (Exception e) {
            
            fail("Lanciata eccezione: "+ e.getMessage());
        
        }

    }

    @Test
    void testPromuoviPartecipante_quandoEsitoPositivo() {

        try {

            uscitaSC.promuoviPartecipante("raffaele@test.com", "simone@test.com", 1L);
        
        } catch (Exception e) {

            fail("Lanciata eccezione: "+ e.getMessage());

        }

    }
    
    @Test
    void testInfoUscita_quandoPartecipantiFalse() {

        outUscitaMD_corretta.setPartecipanti(null);

        try {
        
            OutUscitaMD outUscitaMD_test = uscitaSC.infoUscita("raffaele@test.com", 1L, false);

            assertEquals(outUscitaMD_test, outUscitaMD_corretta);
        
        } catch (Exception e) {
            
            fail("Lanciata eccezione: "+ e.getMessage());
        
        }

    }

    @Test
    void testInfoUscita_quandoPartecipantiTrue() {

        outUscitaMD_corretta.setPartecipanti(outPartecipanteMD_list);

        try {
        
            OutUscitaMD outUscitaMD_test = uscitaSC.infoUscita("raffaele@test.com", 1L, true);

            assertEquals(outUscitaMD_test, outUscitaMD_corretta);
        
        } catch (Exception e) {
            
            fail("Lanciata eccezione: "+ e.getMessage());
        
        }

    }

    
}
