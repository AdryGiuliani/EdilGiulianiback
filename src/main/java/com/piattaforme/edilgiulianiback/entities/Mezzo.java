package com.piattaforme.edilgiulianiback.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
public @Data class Mezzo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String descrizione;
    private float prezzo_hour;
}
