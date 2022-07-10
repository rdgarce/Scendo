package com.scendodevteam.scendo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.scendodevteam.scendo.entity.TokenRegistrazione;
import com.scendodevteam.scendo.entity.Utente;
import com.scendodevteam.scendo.exception.GenericErrorException;
import com.scendodevteam.scendo.model.InUtenteMD;
import com.scendodevteam.scendo.model.OutUtenteMD;
import com.scendodevteam.scendo.repository.TokenRegistrazioneDB;
import com.scendodevteam.scendo.repository.UtenteDB;

@SpringBootTest
public class UtenteSCTest {

    @Autowired
    private UtenteSC utenteSC;

    @MockBean
    private UtenteDB utenteDB;

    @MockBean
    private TokenRegistrazioneDB tokenRegistrazioneDB;

    private InUtenteMD inUtenteMD1_corretto = new InUtenteMD();

    private OutUtenteMD outUtenteMD1_corretto = new OutUtenteMD();
    
    private OutUtenteMD outUtenteMD2_corretto = new OutUtenteMD();

    private InUtenteMD inUtenteMD1_errato = new InUtenteMD();

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
        utente1.setActive(false);

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

        Utente utente3 = new Utente();
        utente3.setNome("Daniele");
        utente3.setCognome("Marfella");
        Date dataDiNascita3 = new SimpleDateFormat("dd/MM/yyyy").parse("24/10/1998");
        utente3.setDataDiNascita(dataDiNascita3);
        utente3.setSesso(1);
        utente3.setEmail("daniele@test.com");
        utente3.setPassword("password");
        utente3.setCittaDiResidenza("Napoli");
        utente3.setCodicePostale("80124");
        utente3.setActive(false);

        Utente utente4 = new Utente();
        utente4.setNome("Fantasma");
        utente4.setCognome("Formaggino");
        Date dataDiNascita4 = new SimpleDateFormat("dd/MM/yyyy").parse("24/10/1998");
        utente4.setDataDiNascita(dataDiNascita4);
        utente4.setSesso(1);
        utente4.setEmail("fantasma@test.com");
        utente4.setPassword("password");
        utente4.setCittaDiResidenza("Napoli");
        utente4.setCodicePostale("80124");
        utente4.setActive(false);

        inUtenteMD1_corretto.setCittaDiResidenza(utente4.getCittaDiResidenza());
        inUtenteMD1_corretto.setCodicePostale(utente4.getCodicePostale());
        inUtenteMD1_corretto.setCognome(utente4.getCognome());
        inUtenteMD1_corretto.setDataDiNascita(utente4.getDataDiNascita());
        inUtenteMD1_corretto.setEmail(utente4.getEmail());
        inUtenteMD1_corretto.setNome(utente4.getNome());
        inUtenteMD1_corretto.setSesso(utente4.getSesso());
        inUtenteMD1_corretto.setPassword(utente4.getPassword());

        Utente utente5 = new Utente();
        utente5.setNome("Pippo");
        utente5.setCognome("Esposito");
        Date dataDiNascita5 = new SimpleDateFormat("dd/MM/yyyy").parse("24/10/1998");
        utente5.setDataDiNascita(dataDiNascita5);
        utente5.setSesso(1);
        utente5.setEmail("pippo@test.com");
        utente5.setPassword("password");
        utente5.setCittaDiResidenza("Napoli");
        utente5.setCodicePostale("80124");
        utente5.setActive(false);

        inUtenteMD1_errato.setCittaDiResidenza(utente5.getCittaDiResidenza());
        inUtenteMD1_errato.setCodicePostale(utente5.getCodicePostale());
        inUtenteMD1_errato.setCognome(utente5.getCognome());
        inUtenteMD1_errato.setDataDiNascita(utente5.getDataDiNascita());
        inUtenteMD1_errato.setEmail(utente5.getEmail());
        inUtenteMD1_errato.setNome(utente5.getNome());
        inUtenteMD1_errato.setSesso(utente5.getSesso());
        inUtenteMD1_errato.setPassword(utente5.getPassword());

        outUtenteMD1_corretto.setCittaDiResidenza(utente1.getCittaDiResidenza());
        outUtenteMD1_corretto.setCodicePostale(utente1.getCodicePostale());
        outUtenteMD1_corretto.setCognome(utente1.getCognome());
        outUtenteMD1_corretto.setDataDiNascita(utente1.getDataDiNascita());
        outUtenteMD1_corretto.setEmail(utente1.getEmail());
        outUtenteMD1_corretto.setNome(utente1.getNome());
        outUtenteMD1_corretto.setSesso(utente1.getSesso());

        outUtenteMD2_corretto.setNome(utente2.getNome());
        outUtenteMD2_corretto.setCognome(utente2.getCognome());
        outUtenteMD2_corretto.setEmail(utente2.getEmail());

        TokenRegistrazione tokenRegistrazione_esistente = new TokenRegistrazione(utente1,"tokenEsistente");

        Mockito.when(utenteDB.findByEmail("raffaele@test.com")).thenReturn(utente1);
        Mockito.when(utenteDB.findByEmail("simone@test.com")).thenReturn(utente2);
        Mockito.when(utenteDB.findByEmail("nonesiste@test.com")).thenReturn(null);
        Mockito.when(utenteDB.findByEmail("daniele@test.com")).thenReturn(utente3);
        Mockito.when(utenteDB.findByEmail("fantasma@test.com")).thenReturn(null);
        Mockito.when(utenteDB.findByEmail("pippo@test.com")).thenReturn(utente5);
        
        Mockito.when(tokenRegistrazioneDB.save(any())).thenReturn(new TokenRegistrazione());
        Mockito.when(tokenRegistrazioneDB.findByToken("tokenEsistente")).thenReturn(tokenRegistrazione_esistente);
        Mockito.when(tokenRegistrazioneDB.findByToken("tokenInesistente")).thenReturn(null);

    }

    @Test
    void testMieInfo_quandoUtenteEsiste() {

        OutUtenteMD outUtenteMD_test = utenteSC.mieInfo("raffaele@test.com");
        assertEquals(outUtenteMD_test, outUtenteMD1_corretto);

    }

    @Test
    void testInfoUtente_quandoUtenteEsiste() {

        try {

            OutUtenteMD outUtenteMD_test = utenteSC.infoUtente("simone@test.com");
            assertEquals(outUtenteMD_test, outUtenteMD2_corretto);

        
        } catch (Exception e) {
            fail("Lanciata eccezione: "+ e.getMessage());
        }
        

    }

    @Test
    void testInfoUtente_quandoUtenteNonEsiste() {

        try {

            utenteSC.infoUtente("nonesiste@test.com");
            fail("Eccezione non lanciata");
            
        } catch (Exception e) {
            assertTrue(e instanceof GenericErrorException && ((GenericErrorException) e).getCode() == "IT_001");
        }

    }

    @Test
    void testInfoUtente_quandoUtenteEsisteMaInattivo() {

        try {

            utenteSC.infoUtente("daniele@test.com");
            fail("Eccezione non lanciata");
        
        } catch (Exception e) {
            assertTrue(e instanceof GenericErrorException && ((GenericErrorException) e).getCode() == "IT_001");
        }

    }

    @Test
    void testRegisterUser_quandoEsitoPositivo() {

        try {

            utenteSC.registerUser(inUtenteMD1_corretto);

        } catch (Exception e) {

            fail("Lanciata eccezione: "+ e.getMessage());

        }

    }

    @Test
    void testRegisterUser_quandoEsisteGiaUtente() {

        try {

            utenteSC.registerUser(inUtenteMD1_errato);
            fail("Eccezione non lanciata");


        } catch (Exception e) {

            assertTrue(e instanceof GenericErrorException && ((GenericErrorException) e).getCode() == "RG_001");

        }

    }

    @Test
    void testVerifyRegistration_quandoTokenCorretto() {

        Optional<String> tokenEsistente = Optional.of("tokenEsistente");

        try {

            assertTrue(utenteSC.verifyRegistration(tokenEsistente));

        } catch (Exception e) {

            fail("Lanciata eccezione: "+ e.getMessage());

        }

    }

    @Test
    void testVerifyRegistration_quandoTokenAssente() {

        Optional<String> tokenAssente = Optional.empty();  

        try {
            utenteSC.verifyRegistration(tokenAssente);
        } catch (Exception e) {
            
            assertTrue(e instanceof GenericErrorException && ((GenericErrorException) e).getCode() == "VR_001");

        }
    }

    @Test
    void testVerifyRegistration_quandoTokenVuoto() {

        Optional<String> tokenVuoto = Optional.of("");

        try {
            utenteSC.verifyRegistration(tokenVuoto);
        } catch (Exception e) {
            
            assertTrue(e instanceof GenericErrorException && ((GenericErrorException) e).getCode() == "VR_001");

        }
    }







}
