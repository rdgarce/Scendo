package com.scendodevteam.scendo.model;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InPasswordResetMD {

    private String token;

    @NotEmpty(message = "La nuova password non può essere vuota")
    private String password;

}
