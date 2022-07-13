package com.scendodevteam.scendo.e2e;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scendodevteam.scendo.entity.TokenRegistrazione;
import com.scendodevteam.scendo.entity.Utente;
import com.scendodevteam.scendo.model.InUscitaMD;
import com.scendodevteam.scendo.model.InUtenteMD;
import com.scendodevteam.scendo.model.JwtRequest;
import com.scendodevteam.scendo.repository.TokenRegistrazioneDB;
import com.scendodevteam.scendo.repository.UtenteDB;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
public class EndToEndTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TokenRegistrazioneDB tokenRegistrazioneDB;

    @Autowired
    UtenteDB utenteDB;
    
    private static ObjectMapper mapper = new ObjectMapper();

    private InUtenteMD inUtenteMD = new InUtenteMD();
    
    private Utente global_utente = new Utente();

    private String global_verify_token = UUID.randomUUID().toString();

    private String global_jwt_token;


    @BeforeAll
    void setUp() throws ParseException{
        
        global_utente.setNome("Raffaele");
        global_utente.setCognome("del Gaudio");
        Date dataDiNascita = new SimpleDateFormat("dd/MM/yyyy").parse("24/10/1998");
        global_utente.setDataDiNascita(dataDiNascita);
        global_utente.setSesso(1);
        global_utente.setEmail("email@test.com");
        global_utente.setPassword("password");
        global_utente.setCittaDiResidenza("Napoli");
        global_utente.setCodicePostale("80124");
        global_utente.setActive(false);

        inUtenteMD.setCittaDiResidenza(global_utente.getCittaDiResidenza());
        inUtenteMD.setCodicePostale(global_utente.getCodicePostale());
        inUtenteMD.setCognome(global_utente.getCognome());
        inUtenteMD.setDataDiNascita(global_utente.getDataDiNascita());
        inUtenteMD.setEmail(global_utente.getEmail());
        inUtenteMD.setNome(global_utente.getNome());
        inUtenteMD.setPassword(global_utente.getPassword());
        inUtenteMD.setSesso(global_utente.getSesso());
        
    }

    @Test
    @Order(1)
    void registerUser(){

        try {


            MockHttpServletResponse resp = mockMvc.perform(post("/api/registrazione")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(mapper.writeValueAsString(inUtenteMD)))
                                                .andExpect(status().isOk()).andReturn().getResponse();

            JsonNode json_response = mapper.readTree(resp.getContentAsString());

            String code = json_response.get("code").asText();

            Optional<TokenRegistrazione> token = tokenRegistrazioneDB.findById(2L);
            global_verify_token = token.get().getToken();

            assertEquals("RG_000", code);

        } catch (Exception e) {
            
            fail("Lanciata eccezione: "+ e.getMessage());
            
        }

    }

     
    @Test
    @Order(2)
    void testVerifyRegistration() {

        try {

            MockHttpServletResponse resp = mockMvc.perform(get("/api/verifica-registrazione")
                                            .param("token", global_verify_token))
                                            .andExpect(status().isOk()).andReturn().getResponse();

            JsonNode json_response = mapper.readTree(resp.getContentAsString());

            String code = json_response.get("code").asText();

            assertEquals("VR_000", code);

        } catch (Exception e) {
            
            fail("Lanciata eccezione: "+ e.getMessage());
            
        }

    }

    @Test
    @Order(3)
    void testLogin() {

        JwtRequest loginForm = new JwtRequest();
        loginForm.setUsername(inUtenteMD.getEmail());
        loginForm.setPassword(inUtenteMD.getPassword());

        try {

            MockHttpServletResponse resp = mockMvc.perform(post("/api/login")
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .content(mapper.writeValueAsString(loginForm)))
                                            .andExpect(status().isOk()).andReturn().getResponse();

            JsonNode json_response = mapper.readTree(resp.getContentAsString());

            String code = json_response.get("code").asText();

            global_jwt_token = json_response.get("message").asText();

            assertEquals("LG_000", code);

        } catch (Exception e) {
            
            fail("Lanciata eccezione: "+ e.getMessage());
            
        }

    }

    @Test
    @Order(4)
    void testCreaUscita() throws ParseException {

        InUscitaMD inUscitaMD = new InUscitaMD();

        Date dataUscita = new SimpleDateFormat("dd/MM/yyyy").parse("30/01/2127");
        inUscitaMD.setDataEOra(dataUscita);
        inUscitaMD.setDescrizione("descrizione");
        inUscitaMD.setLocationIncontro("locationIncontro");
        inUscitaMD.setLocationUscita("locationUscita");
        inUscitaMD.setNumeroMaxPartecipanti(10);
        inUscitaMD.setTipoUscita("tipoUscita");
        inUscitaMD.setUscitaPrivata(true);

        try {

            MockHttpServletResponse resp = mockMvc.perform(post("/api/crea-uscita")
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .content(mapper.writeValueAsString(inUscitaMD))
                                            .header("Authorization", "Bearer "+ global_jwt_token))
                                            .andExpect(status().isOk()).andReturn().getResponse();

            JsonNode json_response = mapper.readTree(resp.getContentAsString());

            String code = json_response.get("code").asText();

            assertEquals("CU_000", code);

        } catch (Exception e) {
            
            fail("Lanciata eccezione: "+ e.getMessage());
            
        }

    }



    
}
