package com.scendodevteam.scendo.units.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scendodevteam.scendo.entity.InvitoUscita;
import com.scendodevteam.scendo.entity.Uscita;
import com.scendodevteam.scendo.exception.GenericErrorException;
import com.scendodevteam.scendo.exception.handler.RestResponseExceptionHandler;
import com.scendodevteam.scendo.model.InUscitaMD;
import com.scendodevteam.scendo.model.MessaggioGenerico;
import com.scendodevteam.scendo.model.OutInvitoMD;
import com.scendodevteam.scendo.model.OutPartecipanteMD;
import com.scendodevteam.scendo.model.OutUscitaMD;
import com.scendodevteam.scendo.service.InvitoSC;
import com.scendodevteam.scendo.service.UscitaSC;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class UscitaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UscitaSC uscitaSC;

    @MockBean
    private InvitoSC invitoSC;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private RestResponseExceptionHandler responseExceptionHandler;

    private static ObjectMapper mapper = new ObjectMapper();

    private Uscita uscita = new Uscita();

    private InUscitaMD inUscitaMD = new InUscitaMD();
    
    private OutUscitaMD outUscitaMD = new OutUscitaMD();

    private ArrayList<OutInvitoMD> lista_inviti = new ArrayList<OutInvitoMD>();

    @BeforeEach
    void setUp() throws ParseException{

        UserDetails userDetails = new User("raffaele@test.com","password", new ArrayList<>());

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                                = new UsernamePasswordAuthenticationToken(userDetails,
                                null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        Mockito.when(passwordEncoder.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(true);

        Date dataUscita1 = new SimpleDateFormat("dd/MM/yyyy").parse("30/01/2127");
        uscita.setDataEOra(dataUscita1);
        uscita.setDescrizione("descrizione");
        uscita.setLocationIncontro("locationIncontro");
        uscita.setLocationUscita("locationUscita");
        uscita.setNumeroMaxPartecipanti(10);
        uscita.setTipoUscita("tipoUscita");
        uscita.setUscitaPrivata(true);

        inUscitaMD.setDataEOra(uscita.getDataEOra());
        inUscitaMD.setDescrizione(uscita.getDescrizione());
        inUscitaMD.setLocationIncontro(uscita.getLocationIncontro());
        inUscitaMD.setLocationUscita(uscita.getLocationUscita());
        inUscitaMD.setNumeroMaxPartecipanti(uscita.getNumeroMaxPartecipanti());
        inUscitaMD.setTipoUscita(uscita.getTipoUscita());
        inUscitaMD.setUscitaPrivata(uscita.isUscitaPrivata());

        outUscitaMD.setTipoUscita(uscita.getTipoUscita());
        outUscitaMD.setDataEOra(uscita.getDataEOra());
        outUscitaMD.setLocationIncontro(uscita.getLocationIncontro());
        outUscitaMD.setLocationUscita(uscita.getLocationUscita());
        outUscitaMD.setUscitaPrivata(uscita.isUscitaPrivata());
        outUscitaMD.setNumeroMaxPartecipanti(uscita.getNumeroMaxPartecipanti());
        outUscitaMD.setDescrizione(uscita.getDescrizione());
        
        OutInvitoMD outInvitoMD1 = new OutInvitoMD();
        outInvitoMD1.setIdInvito(1);
        outInvitoMD1.setEmailInvitante("raffaele@test.com");
        outInvitoMD1.setIdUscita(2);

        OutInvitoMD outInvitoMD2 = new OutInvitoMD();
        outInvitoMD2.setIdInvito(3);
        outInvitoMD2.setEmailInvitante("simone@test.com");
        outInvitoMD2.setIdUscita(4);

        OutInvitoMD outInvitoMD3 = new OutInvitoMD();
        outInvitoMD3.setIdInvito(5);
        outInvitoMD3.setEmailInvitante("daniele@test.com");
        outInvitoMD3.setIdUscita(6);

        lista_inviti.add(outInvitoMD1);
        lista_inviti.add(outInvitoMD2);
        lista_inviti.add(outInvitoMD3);
        

    }

    @Test
    void testAccettaInvito_quandoInvitoEsiste() {

        try {

            Mockito.when(invitoSC.accettaInvito("raffaele@test.com", 1)).thenReturn("Invito accettato");

            MockHttpServletResponse resp = mockMvc.perform(post("/api/accetta-invito")
                                                    .param("uscita","1"))
                                                    .andExpect(status().isOk()).andReturn().getResponse();

            JsonNode json_response = mapper.readTree(resp.getContentAsString());

            String code = json_response.get("code").asText();

            assertEquals("AV_000", code);


        } catch (Exception e) {
            
            fail("Lanciata eccezione: "+ e.getMessage());
            
        }

    }

    @Test
    void testAccettaInvito_quandoInvitoNonEsiste() {

        Mockito.when(responseExceptionHandler.genericErrorHandler(any()))
        .thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new MessaggioGenerico("Invito inesistente","AV_004")));
        
        try {

            Mockito.when(invitoSC.accettaInvito("raffaele@test.com", 2))
                        .thenThrow(new GenericErrorException("Invito inesistente","AV_004"));

            MockHttpServletResponse resp = mockMvc.perform(post("/api/accetta-invito")
                                                    .param("uscita","2"))
                                                    .andExpect(status().isBadRequest())
                                                    .andReturn().getResponse();

            JsonNode json_response = mapper.readTree(resp.getContentAsString());

            String code = json_response.get("code").asText();

            assertEquals("AV_004", code);


        } catch (Exception e) {
            
            fail("Lanciata eccezione: "+ e.getMessage());
            
        }

    }

    @Test
    void testConsultaCalendario_quandoEsitoPositivo() {

        ArrayList<Long> uscite = new ArrayList<Long>();
        uscite.add(1L);
        uscite.add(2L);
        uscite.add(3L);
        uscite.add(4L);

        try {

            Mockito.when(uscitaSC.consultaCalendario("raffaele@test.com")).thenReturn(uscite);

            MockHttpServletResponse resp = mockMvc.perform(get("/api/calendario-uscite"))
                                                    .andExpect(status().isOk())
                                                    .andReturn().getResponse();

            JsonNode json_response = mapper.readTree(resp.getContentAsString());

            String code = json_response.get("code").asText();

            String json_uscite = json_response.get("message").toString();

            assertTrue(code.equals("CC_000") && mapper.writeValueAsString(uscite).equals(json_uscite));


        } catch (Exception e) {
            
            fail("Lanciata eccezione: "+ e.getMessage());

        }


    }

    @Test
    void testCreaUscita_quandoEsitoPositivo() {

        try {

            Mockito.when(uscitaSC.creaUscita("raffaele@test.com",inUscitaMD)).thenReturn(uscita);

            MockHttpServletResponse resp = mockMvc.perform(post("/api/crea-uscita")
                                                    .contentType(MediaType.APPLICATION_JSON)
                                                    .content(mapper.writeValueAsString(inUscitaMD)))
                                                    .andExpect(status().isOk())
                                                    .andReturn().getResponse();

            JsonNode json_response = mapper.readTree(resp.getContentAsString());

            String code = json_response.get("code").asText();

            assertTrue(code.equals("CU_000"));


        } catch (Exception e) {
            
            fail("Lanciata eccezione: "+ e.getMessage());

        }

    }

    @Test
    void testInfoUscita_quandoPartecipantiTrue() {

        OutPartecipanteMD outUtenteMD = new OutPartecipanteMD();

        outUtenteMD.setNome("Raffaele");
        outUtenteMD.setCognome("del Gaudio");
        outUtenteMD.setEmail("raffaele@test.com");
        outUtenteMD.setUtenteCreatore(true);
        outUtenteMD.setUtenteOrganizzatore(false);
        
        ArrayList<OutPartecipanteMD> partecipanti = new ArrayList<OutPartecipanteMD>();
        partecipanti.add(outUtenteMD);

        outUscitaMD.setPartecipanti(partecipanti);

        try {

            Mockito.when(uscitaSC.infoUscita("raffaele@test.com", 1, true)).thenReturn(outUscitaMD);

            MockHttpServletResponse resp = mockMvc.perform(get("/api/uscita/{idUscita}", 1)
                                            .param("partecipanti", "true"))
                                            .andExpect(status().isOk())
                                            .andReturn().getResponse();
            
            JsonNode json_response = mapper.readTree(resp.getContentAsString());

            JsonNode info_uscita = json_response.get("message");

            String code = json_response.get("code").asText();

            String lista_partecipanti_string = info_uscita.get("partecipanti").toString();

            assertTrue(code.equals("IU_000") && mapper.writeValueAsString(outUscitaMD.getPartecipanti()).equals(lista_partecipanti_string));

        } catch (Exception e) {
            
            fail("Lanciata eccezione: "+ e.getMessage());

        }

    }

    @Test
    void testInfoUscita_quandoPartecipantiFalse() {

        outUscitaMD.setPartecipanti(null);

        try {

            Mockito.when(uscitaSC.infoUscita("raffaele@test.com", 1, false)).thenReturn(outUscitaMD);

            MockHttpServletResponse resp = mockMvc.perform(get("/api/uscita/{idUscita}", 1)
                                            .param("partecipanti", "false"))
                                            .andExpect(status().isOk())
                                            .andReturn().getResponse();
            
            JsonNode json_response = mapper.readTree(resp.getContentAsString());

            JsonNode info_uscita = json_response.get("message");

            String code = json_response.get("code").asText();

            String lista_partecipanti_string = info_uscita.get("partecipanti").toString();

            assertTrue(code.equals("IU_000") && mapper.writeValueAsString(outUscitaMD.getPartecipanti()).equals(lista_partecipanti_string));

        } catch (Exception e) {
            
            fail("Lanciata eccezione: "+ e.getMessage());

        }


    }

    @Test
    void testLeggiInviti() {

        try {

            Mockito.when(invitoSC.leggiInviti("raffaele@test.com")).thenReturn(lista_inviti);

            MockHttpServletResponse resp = mockMvc.perform(get("/api/leggi-inviti"))
                                            .andExpect(status().isOk())
                                            .andReturn().getResponse();

            JsonNode json_response = mapper.readTree(resp.getContentAsString());

            String code = json_response.get("code").asText();

            String inviti_restituiti = json_response.get("message").toString();

            assertTrue(code.equals("LV_000") && mapper.writeValueAsString(lista_inviti).equals(inviti_restituiti));
        
        } catch (Exception e) {

            fail("Lanciata eccezione: "+ e.getMessage());

        }

    }

    @Test
    void testPromuoviPartecipante_quandoEsitoPositivo() {

        try {

            Mockito.when(uscitaSC.promuoviPartecipante("raffaele@test.com","simone@test.com",1))
                    .thenReturn(true);

            MockHttpServletResponse resp = mockMvc.perform(post("/api/uscita/{idUscita}/promuovi-partecipante", 1)
                                            .contentType(MediaType.APPLICATION_JSON).content("{\"email_partecipante\": \"simone@test.com\"}"))
                                            .andExpect(status().isOk())
                                            .andReturn().getResponse();
            
            JsonNode json_response = mapper.readTree(resp.getContentAsString());

            String code = json_response.get("code").asText();

            assertEquals(code, "PP_000");


        } catch (Exception e) {

            fail("Lanciata eccezione: "+ e.getMessage());

        }

    }

    @Test
    void testPromuoviPartecipante_quandoPartecipanteNonEsisite() {

        Mockito.when(responseExceptionHandler.genericErrorHandler(any()))
        .thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new MessaggioGenerico("Nessun utente è associato a questa email: daniele@test.com", "PP_002")));

        try {

            Mockito.when(uscitaSC.promuoviPartecipante("raffaele@test.com","daniele@test.com",1))
                    .thenThrow(new GenericErrorException("Nessun utente è associato a questa email: daniele@test.com", "PP_002"));

            MockHttpServletResponse resp = mockMvc.perform(post("/api/uscita/{idUscita}/promuovi-partecipante", 1)
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .content("{\"email_partecipante\": \"daniele@test.com\"}"))
                                            .andExpect(status().isBadRequest())
                                            .andReturn().getResponse();
            
            JsonNode json_response = mapper.readTree(resp.getContentAsString());

            String code = json_response.get("code").asText();

            assertEquals(code, "PP_002");


        } catch (Exception e) {

            fail("Lanciata eccezione: "+ e.getMessage());

        }

    }

    @Test
    void testRifiutaInvito_quandoInvitoEsiste() {

        try {

            Mockito.when(invitoSC.rifiutaInvito("raffaele@test.com", 1)).thenReturn("Invito rifiutato");

            MockHttpServletResponse resp = mockMvc.perform(post("/api/rifiuta-invito")
                                                    .param("uscita","1"))
                                                    .andExpect(status().isOk()).andReturn().getResponse();

            JsonNode json_response = mapper.readTree(resp.getContentAsString());

            String code = json_response.get("code").asText();

            assertEquals("RV_000", code);


        } catch (Exception e) {
            
            fail("Lanciata eccezione: "+ e.getMessage());
            
        }

    }


    @Test
    void testRifiutaInvito_quandoInvitoNonEsiste() {

        Mockito.when(responseExceptionHandler.genericErrorHandler(any()))
        .thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new MessaggioGenerico("Invito inesistente","RV_003")));
        
        try {

            Mockito.when(invitoSC.rifiutaInvito("raffaele@test.com", 2))
                        .thenThrow(new GenericErrorException("Invito inesistente","RV_003"));

            MockHttpServletResponse resp = mockMvc.perform(post("/api/rifiuta-invito")
                                                    .param("uscita","2"))
                                                    .andExpect(status().isBadRequest())
                                                    .andReturn().getResponse();

            JsonNode json_response = mapper.readTree(resp.getContentAsString());

            String code = json_response.get("code").asText();

            assertEquals("RV_003", code);


        } catch (Exception e) {
            
            fail("Lanciata eccezione: "+ e.getMessage());
            
        }

    }

    @Test
    void testSalvaInvito_quandoEsitoPositivo() {

        try {

            Mockito.when(invitoSC.salvaInvito("raffaele@test.com","simone@test.com",1))
                    .thenReturn(new InvitoUscita());

            MockHttpServletResponse resp = mockMvc.perform(post("/api/uscita/{idUscita}/invita-partecipante", 1)
                                            .contentType(MediaType.APPLICATION_JSON).content("{\"email_invitato\": \"simone@test.com\"}"))
                                            .andExpect(status().isOk())
                                            .andReturn().getResponse();
            
            JsonNode json_response = mapper.readTree(resp.getContentAsString());

            String code = json_response.get("code").asText();

            assertEquals(code, "SV_000");


        } catch (Exception e) {

            fail("Lanciata eccezione: "+ e.getMessage());

        }

    }

    @Test
    void testSalvaInvito_quandoUtenteNonEsiste() {

        Mockito.when(responseExceptionHandler.genericErrorHandler(any()))
        .thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new MessaggioGenerico("Nessun utente è associato a questa email", "SV_002")));

        try {

            Mockito.when(invitoSC.salvaInvito("raffaele@test.com","daniele@test.com",1))
                    .thenThrow(new GenericErrorException("Nessun utente è associato a questa email", "SV_002"));

            MockHttpServletResponse resp = mockMvc.perform(post("/api/uscita/{idUscita}/invita-partecipante", 1)
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .content("{\"email_invitato\": \"daniele@test.com\"}"))
                                            .andExpect(status().isBadRequest())
                                            .andReturn().getResponse();
            
            JsonNode json_response = mapper.readTree(resp.getContentAsString());

            String code = json_response.get("code").asText();

            assertEquals(code, "SV_002");


        } catch (Exception e) {

            fail("Lanciata eccezione: "+ e.getMessage());

        }
    }

    @Test
    void testAbbandonaUscita_quandoUscitaEsiste(){

        try {


            Mockito.doNothing().when(uscitaSC).abbandonaUscita("raffaele@test.com", 1);

            MockHttpServletResponse resp = mockMvc.perform(delete("/api/uscita/{idUscita}", 1)
                                            .contentType(MediaType.APPLICATION_JSON))
                                            .andExpect(status().isOk())
                                            .andReturn().getResponse();
            
            JsonNode json_response = mapper.readTree(resp.getContentAsString());
            
            String code = json_response.get("code").asText();

            assertEquals(code, "AU_000");

        } catch (Exception e) {

            fail("Lanciata eccezione: "+ e.getMessage());
        }

    }

    @Test
    void testAbbandonaUscita_quandoUscitaNonEsiste(){

        Mockito.when(responseExceptionHandler.genericErrorHandler(any()))
        .thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new MessaggioGenerico("Nessuna uscita associata a questo id: 2", "AU_001")));

        try {

            Mockito.doThrow(new GenericErrorException("Nessuna uscita associata a questo id: 2", "AU_001"))
                .when(uscitaSC).abbandonaUscita("raffaele@test.com", 2);

            MockHttpServletResponse resp = mockMvc.perform(delete("/api/uscita/{idUscita}", 2)
                                            .contentType(MediaType.APPLICATION_JSON))
                                            .andExpect(status().isBadRequest())
                                            .andReturn().getResponse();
            
            JsonNode json_response = mapper.readTree(resp.getContentAsString());
            
            String code = json_response.get("code").asText();

            assertEquals(code, "AU_001");

        } catch (Exception e) {

            fail("Lanciata eccezione: "+ e.getMessage());
        }

    }

    @Test
    void testAbbandonaUscita_quandoUtenteNonPartecipa(){

        Mockito.when(responseExceptionHandler.genericErrorHandler(any()))
        .thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new MessaggioGenerico("Non sei un partecipante di questa uscita", "AU_002")));

        try {

            Mockito.doThrow(new GenericErrorException("Non sei un partecipante di questa uscita", "AU_002"))
                .when(uscitaSC).abbandonaUscita("raffaele@test.com", 3);

            MockHttpServletResponse resp = mockMvc.perform(delete("/api/uscita/{idUscita}", 3)
                                            .contentType(MediaType.APPLICATION_JSON))
                                            .andExpect(status().isBadRequest())
                                            .andReturn().getResponse();
            
            JsonNode json_response = mapper.readTree(resp.getContentAsString());
            
            String code = json_response.get("code").asText();

            assertEquals(code, "AU_002");

        } catch (Exception e) {

            fail("Lanciata eccezione: "+ e.getMessage());
        }

    }
    
}
