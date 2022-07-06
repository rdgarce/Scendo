package com.scendodevteam.scendo.event;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationEvent;

import com.scendodevteam.scendo.entity.TokenRegistrazione;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrazioneCompletata extends ApplicationEvent {

    private final TokenRegistrazione tokenRegistrazione;
    private final HttpServletRequest request;

    public RegistrazioneCompletata(TokenRegistrazione tokenRegistrazione, HttpServletRequest request) {
        super(tokenRegistrazione);
        this.tokenRegistrazione = tokenRegistrazione;
        this.request = request;

    }
}
