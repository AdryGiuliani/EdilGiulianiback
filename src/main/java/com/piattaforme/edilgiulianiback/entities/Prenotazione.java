package com.piattaforme.edilgiulianiback.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
public @Data class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Temporal(TemporalType.DATE)
    private Date datainizio;

    @Temporal(TemporalType.DATE)
    private Date datafine;

    private int oreLavoro;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn
    private Cliente cliente_p;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String descrizione;
}
