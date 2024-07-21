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

    private String nome;

    private int oreLavoro;

    private String clienteID;

    private String indirizzo;

    private String CAP;

    private float prezzoStimato;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dataCreazione;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String descrizione;

    @OneToMany(fetch = FetchType.EAGER)
    private List<SubPrenotazione> subPrenotazioni;

    public void calcolaPrezzo(){
        float totPrice = 0; int totH = 0;
        for (SubPrenotazione sp : subPrenotazioni){
            int h = sp.orelavoro();
            totH+=h;
            totPrice+= sp.calcolaPrezzo();
        }
        this.prezzoStimato = totPrice;
        this.oreLavoro = totH;
    }
}
