package com.piattaforme.edilgiulianiback.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
public @Data class Preventivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Temporal(TemporalType.DATE)
    private Date dataCreazione;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    private Cliente cliente_c;
}
