package com.scendodevteam.scendo.model;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InUtenteMD{

    @NotEmpty(message = "Il nome non può essere vuoto")
    private String nome;

    @NotEmpty(message = "Il cognome non può essere vuoto")
    private String cognome;

    @NotNull(message = "La data di nascita non può essere vuota")
    @Past(message = "La data di nascità non può essere nel presente o futuro")
    private Date dataDiNascita;

    @NotNull(message = "Il sesso non può essere vuoto")
    @Max(value = 1,message = "Il sesso deve essere un valore tra 0 e 1")
    @Min(value = 0,message = "Il sesso deve essere un valore tra 0 e 1")
    private Integer sesso;

    @Email(message = "Hai inserito una email incorretta")
    @NotEmpty(message = "L'email non può essere vuota")
    @Setter(AccessLevel.NONE)
    private String email;

    @NotEmpty(message = "La password non può essere vuota")
    private String password;

    @NotEmpty(message = "La città di residenza non può essere vuota")
    private String cittaDiResidenza;

    @NotEmpty(message = "Il codice postale non può essere vuoto")
    private String codicePostale;

    public void setEmail(String email){

        //Explicit setter to always lowercase the email
        this.email = email.toLowerCase();
    }

}
