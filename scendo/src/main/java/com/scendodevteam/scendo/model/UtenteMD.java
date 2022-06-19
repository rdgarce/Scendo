package com.scendodevteam.scendo.model;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtenteMD{

    @NotEmpty(message = "Il nome non può essere vuoto")
    private String nome;

    @NotEmpty(message = "Il cognome non può essere vuoto")
    private String cognome;

    @NotNull(message = "La data di nascita non può essere vuota")
    @Past(message = "La data di nascità non può essere nel presente o futuro")
    private Date dataDiNascita;

    @NotNull(message = "Il sesso non può essere vuoto")
    private Integer sesso;

    @Email(message = "Hai inserito una email incorretta")
    @NotEmpty(message = "L'email non può essere vuoto")
    private String email;

    @NotEmpty(message = "La password non può essere vuota")
    private String password;

    @NotEmpty(message = "La città di residenza non può essere vuota")
    private String cittaDiResidenza;

    @NotEmpty(message = "Il codice postale non può essere vuoto")
    private String codicePostale;

}
