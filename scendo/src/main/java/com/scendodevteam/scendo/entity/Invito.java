package com.scendodevteam.scendo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inviti")
public class Invito {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idInvito;

    @ManyToOne
    @JoinColumn(name = "id_invitante",
    nullable = false,
    foreignKey = @ForeignKey(name = "FK_INVITANTE"))
    private Utente utenteInvitante;

    @ManyToOne
    @JoinColumn(name = "id_invitato",
    nullable = false,
    foreignKey = @ForeignKey(name = "FK_INVITATO"))
    private Utente utenteInvitato;

    @ManyToOne
    @JoinColumn(name = "id_uscita",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_Uscita"))
    private Uscita uscita;
}
