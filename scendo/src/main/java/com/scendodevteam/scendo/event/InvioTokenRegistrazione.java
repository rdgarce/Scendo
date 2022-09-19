package com.scendodevteam.scendo.event;

import org.springframework.context.ApplicationEvent;

import com.scendodevteam.scendo.entity.TokenRegistrazione;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvioTokenRegistrazione extends ApplicationEvent {

    private final TokenRegistrazione tokenRegistrazione;

    public InvioTokenRegistrazione(TokenRegistrazione tokenRegistrazione) {
        super(tokenRegistrazione);
        this.tokenRegistrazione = tokenRegistrazione;

    }
}
