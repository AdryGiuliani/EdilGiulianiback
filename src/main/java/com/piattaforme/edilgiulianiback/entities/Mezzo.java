package com.piattaforme.edilgiulianiback.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
public @Data class Mezzo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String descrizione;
    private float prezzo_hour;
    private float minPrice;
    private float dailyPrice;
    private boolean available;

//    @OneToMany(mappedBy = "mezzo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<SubPrenotazione> prenotazioni;
}
