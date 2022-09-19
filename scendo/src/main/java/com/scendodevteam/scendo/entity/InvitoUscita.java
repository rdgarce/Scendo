package com.scendodevteam.scendo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inviti_uscite")
public class InvitoUscita {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idInvito;

    @ManyToOne
    @JoinColumn(name = "id_invitante",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_INVITI_USCITE_INVITANTE")
    )
    @JsonIgnore
    private Utente utenteInvitante;

    @ManyToOne
    @JoinColumn(name = "id_invitato",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_INVITI_USCITE_INVITATO")
    )
    @JsonIgnore
    private Utente utenteInvitato;

    @ManyToOne
    @JoinColumn(name = "id_uscita",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_INVITI_USCITE_USCITA")
    )
    @JsonIgnore
    private Uscita uscita;


    
    public InvitoUscita(Utente utenteInvitante, Utente utenteInvitato, Uscita uscita) {
        this.utenteInvitante = utenteInvitante;
        this.utenteInvitato = utenteInvitato;
        this.uscita = uscita;
    }
    
}
