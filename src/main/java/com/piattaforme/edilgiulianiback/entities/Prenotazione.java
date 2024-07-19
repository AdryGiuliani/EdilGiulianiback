package com.piattaforme.edilgiulianiback.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
public @Data class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int oreLavoro;

    private String clienteID;

    private String indirizzo;

    private String CAP;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dataCreazione;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String descrizione;

    @OneToMany
    private List<SubPrenotazione> subPrenotazioni;
}
