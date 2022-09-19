package com.scendodevteam.scendo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import javax.persistence.*;

enum statoInvitoEnum{
        PENDING,
        ACCETTATO,
        RIFIUTATO
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inviti_amicizie")
public class InvitoAmicizia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idInvito;

    @ManyToOne
    @JoinColumn(name = "id_invitante",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_INVITI_AMICIZIE_INVITANTE")
    )
    @JsonIgnore
    private Utente utenteInvitante;

    @ManyToOne
    @JoinColumn(name = "id_invitato",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_INVITI_AMICIZIE_INVITATO")
    )
    @JsonIgnore
    private Utente utenteInvitato;

    private Date dataInvioInvito;

    private Date dataUltimaAzione;

    private statoInvitoEnum statoInvito;
    
    public InvitoAmicizia(Utente utenteInvitante, Utente utenteInvitato) {
        this.utenteInvitante = utenteInvitante;
        this.utenteInvitato = utenteInvitato;
        this.dataInvioInvito = new Date(System.currentTimeMillis());
        statoInvito = statoInvitoEnum.PENDING;
    }
    
}
