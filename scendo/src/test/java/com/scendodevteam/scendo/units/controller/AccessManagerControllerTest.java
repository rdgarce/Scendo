package com.scendodevteam.scendo.units.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scendodevteam.scendo.entity.TokenRegistrazione;
import com.scendodevteam.scendo.entity.Utente;
import com.scendodevteam.scendo.exception.GenericErrorException;
import com.scendodevteam.scendo.exception.handler.RestResponseExceptionHandler;
import com.scendodevteam.scendo.model.InUtenteMD;
import com.scendodevteam.scendo.model.MessaggioGenerico;
import com.scendodevteam.scendo.service.UtenteSC;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class AccessManagerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UtenteSC utenteSC;

    @MockBean
    private RestResponseExceptionHandler responseExceptionHandler;

    private static ObjectMapper mapper = new ObjectMapper();

    private InUtenteMD inUtenteMD_corretto = new InUtenteMD();
    
    private InUtenteMD inUtenteMD_errato = new InUtenteMD();

    private TokenRegistrazione tokenRegistrazione;

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

        inUtenteMD_corretto.setCittaDiResidenza(utente.getCittaDiResidenza());
        inUtenteMD_corretto.setCodicePostale(utente.getCodicePostale());
        inUtenteMD_corretto.setCognome(utente.getCognome());
        inUtenteMD_corretto.setDataDiNascita(utente.getDataDiNascita());
        inUtenteMD_corretto.setEmail(utente.getEmail());
        inUtenteMD_corretto.setNome(utente.getNome());
        inUtenteMD_corretto.setSesso(utente.getSesso());
        inUtenteMD_corretto.setPassword(utente.getPassword());

        tokenRegistrazione = new TokenRegistrazione(utente, UUID.randomUUID().toString());

        inUtenteMD_errato.setCittaDiResidenza(utente.getCittaDiResidenza());
        inUtenteMD_errato.setCodicePostale(utente.getCodicePostale());
        inUtenteMD_errato.setCognome(utente.getCognome());
        inUtenteMD_errato.setDataDiNascita(utente.getDataDiNascita());
        inUtenteMD_errato.setEmail(utente.getEmail());
        inUtenteMD_errato.setNome(utente.getNome());
        inUtenteMD_errato.setSesso(utente.getSesso());
        inUtenteMD_errato.setPassword(utente.getPassword());



    }

    @Test
    void testRegisterUser_quandoEsitoPositivo() {

        try {

            Mockito.when(utenteSC.registerUser(inUtenteMD_corretto)).thenReturn(tokenRegistrazione);

            MockHttpServletResponse resp = mockMvc.perform(post("/api/registrazione")
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .content(mapper.writeValueAsString(inUtenteMD_corretto)))
                                            .andExpect(status().isOk()).andReturn().getResponse();

            JsonNode json_response = mapper.readTree(resp.getContentAsString());
            
            String code = json_response.get("code").asText();

            assertEquals("RG_000", code);

        } catch (Exception e) {

            fail("Lanciata eccezione: "+ e.getMessage());

        }

    }

    @Test
    void testRegisterUser_quandoEmailGiaUtilizzata() {

        Mockito.when(responseExceptionHandler.genericErrorHandler(any()))
        .thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new MessaggioGenerico("Esiste già un utente registrato con questa email","RG_001")));

        try {

            Mockito.when(utenteSC.registerUser(inUtenteMD_errato)).thenThrow(new GenericErrorException("Esiste già un utente registrato con questa email","RG_001"));

            MockHttpServletResponse resp = mockMvc.perform(post("/api/registrazione")
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .content(mapper.writeValueAsString(inUtenteMD_errato)))
                                            .andExpect(status().isBadRequest()).andReturn().getResponse();

            JsonNode json_response = mapper.readTree(resp.getContentAsString());
            
            String code = json_response.get("code").asText();

            assertEquals("RG_001", code);

        } catch (Exception e) {

            fail("Lanciata eccezione: "+ e.getMessage());

        }

    }

    @Test
    void testVerifyRegistration_quandoEsitoPositivo() {

        try {

            Mockito.when(utenteSC.verifyRegistration(Optional.of(tokenRegistrazione.getToken()))).thenReturn(true);

            MockHttpServletResponse resp = mockMvc.perform(get("/api/verifica-registrazione")
                                            .param("token", tokenRegistrazione.getToken()))
                                            .andExpect(status().isOk()).andReturn().getResponse();

            JsonNode json_response = mapper.readTree(resp.getContentAsString());

            String code = json_response.get("code").asText();

            assertEquals("VR_000", code);

        } catch (Exception e) {
            
            fail("Lanciata eccezione: "+ e.getMessage());
            
        }

    }

    @Test
    void testVerifyRegistration_quandoTokenErrato() {

        Mockito.when(responseExceptionHandler.genericErrorHandler(any()))
        .thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new MessaggioGenerico("Il token inserito non è corretto","VR_002")));

        try {

            Mockito.when(utenteSC.verifyRegistration(Optional.of("tokenErrato")))
                    .thenThrow(new GenericErrorException("Il token inserito non è corretto","VR_002"));

            MockHttpServletResponse resp = mockMvc.perform(get("/api/verifica-registrazione")
                                            .param("token", "tokenErrato"))
                                            .andExpect(status().isBadRequest()).andReturn().getResponse();

            JsonNode json_response = mapper.readTree(resp.getContentAsString());

            String code = json_response.get("code").asText();

            assertEquals("VR_002", code);

        } catch (Exception e) {
            
            fail("Lanciata eccezione: "+ e.getMessage());
            
        }

    }
}
