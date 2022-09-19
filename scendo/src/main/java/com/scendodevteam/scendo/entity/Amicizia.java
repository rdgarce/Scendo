package com.scendodevteam.scendo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import javax.persistence.*;

enum statoAmiciziaEnum{
    ATTIVA,
    TERMINATA
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "amicizie")
public class Amicizia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idAmicizia;

    @ManyToOne
    @JoinColumn(name = "id_utente_1",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_AMICIZIE_UTENTE_1")
    )
    @JsonIgnore
    private Utente utente1;

    @ManyToOne
    @JoinColumn(name = "id_utente_2",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_AMICIZIE_UTENTE_2")
    )
    @JsonIgnore
    private Utente utente2;

    private Date dataInizioAmicizia;

    private Date dataFineAmicizia;

    private statoAmiciziaEnum statoAmicizia;
    
    public Amicizia(Utente utente1, Utente utente2) {
        this.utente1 = utente1;
        this.utente2 = utente2;
        this.dataInizioAmicizia = new Date(System.currentTimeMillis());
        this.statoAmicizia = statoAmiciziaEnum.ATTIVA;
    }
    
}
