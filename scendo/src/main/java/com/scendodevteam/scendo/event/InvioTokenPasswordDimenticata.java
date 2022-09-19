package com.scendodevteam.scendo.event;

import org.springframework.context.ApplicationEvent;

import com.scendodevteam.scendo.entity.TokenPasswordDimenticata;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvioTokenPasswordDimenticata extends ApplicationEvent {

    private final TokenPasswordDimenticata tokenPasswordDimenticata;

    public InvioTokenPasswordDimenticata(TokenPasswordDimenticata tokenPasswordDimenticata) {
        super(tokenPasswordDimenticata);
        this.tokenPasswordDimenticata = tokenPasswordDimenticata;

    }
}
