package com.scendodevteam.scendo.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;

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
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scendodevteam.scendo.exception.GenericErrorException;
import com.scendodevteam.scendo.exception.handler.RestResponseExceptionHandler;
import com.scendodevteam.scendo.model.MessaggioGenerico;
import com.scendodevteam.scendo.model.OutUtenteMD;
import com.scendodevteam.scendo.service.UtenteSC;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class UtenteControllerTest {
    
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UtenteSC utenteSC;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private RestResponseExceptionHandler responseExceptionHandler;

    private OutUtenteMD outUtenteRaffaele = new OutUtenteMD();

    private static ObjectMapper mapper = new ObjectMapper();


    @BeforeEach
    void setUp() throws ParseException{

        outUtenteRaffaele.setCittaDiResidenza("Napoli");
        outUtenteRaffaele.setCodicePostale("80124");
        outUtenteRaffaele.setCognome("del Gaudio");
        Date dataDiNascita2 = new SimpleDateFormat("dd/MM/yyyy").parse("24/10/1998");
        outUtenteRaffaele.setDataDiNascita(dataDiNascita2);
        outUtenteRaffaele.setEmail("raffaele@test.com");
        outUtenteRaffaele.setNome("Raffaele");
        outUtenteRaffaele.setSesso(0);

        UserDetails userDetails = new User(outUtenteRaffaele.getEmail(),"password", new ArrayList<>());

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                                = new UsernamePasswordAuthenticationToken(userDetails,
                                null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        Mockito.when(passwordEncoder.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(true);

    }
    
    @Test
    void testMieInfo_quandoEsitoPositivo() {

        try {

            Mockito.when(utenteSC.mieInfo("raffaele@test.com")).thenReturn(outUtenteRaffaele);

            MockHttpServletResponse resp = mockMvc.perform(get("/api/utente/me"))
            .andExpect(status().isOk()).andReturn().getResponse();

            JsonNode json_response = mapper.readTree(resp.getContentAsString());
            
            String code = json_response.get("code").asText();

            String email = json_response.get("message").get("email").asText();

            assertTrue(code.equals("MI_000") && email.equals("raffaele@test.com"));
            
        } catch (Exception e) {
            
            fail("Lanciata eccezione: "+ e.getMessage());
            
        }

    }


    @Test
    void testInfoUtente_quandoUtenteEsiste() {

        try {

            Mockito.when(utenteSC.infoUtente("raffaele@test.com")).thenReturn(outUtenteRaffaele);

            MockHttpServletResponse resp = mockMvc.perform(get("/api/utente/raffaele@test.com"))
            .andExpect(status().isOk()).andReturn().getResponse();

            JsonNode json_response = mapper.readTree(resp.getContentAsString());
            
            String code = json_response.get("code").asText();

            String email = json_response.get("message").get("email").asText();

            assertTrue(code.equals("IT_000") && email.equals("raffaele@test.com"));
            
        } catch (Exception e) {
            
            fail("Lanciata eccezione: "+ e.getMessage());
            
        }

    }

    @Test
    void testInfoUtente_quandoUtenteNonEsiste() {

        Mockito.when(responseExceptionHandler.genericErrorHandler(any()))
        .thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new MessaggioGenerico("Non esiste un utente con questa email","IT_001")));
            
            try {

                Mockito.when(utenteSC.infoUtente("nonesiste@test.com")).thenThrow(new GenericErrorException("Non esiste un utente con questa email","IT_001"));

                MockHttpServletResponse resp = mockMvc.perform(get("/api/utente/nonesiste@test.com"))
                .andExpect(status().isBadRequest()).andReturn().getResponse();

                JsonNode json_response = mapper.readTree(resp.getContentAsString());
            
                String code = json_response.get("code").asText();

                assertTrue(code.equals("IT_001"));

            } catch (Exception e) {
            
                fail("Lanciata eccezione: "+ e.getMessage());

            }

    }

    
}
