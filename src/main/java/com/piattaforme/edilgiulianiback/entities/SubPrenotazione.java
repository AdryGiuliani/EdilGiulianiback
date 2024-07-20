package com.piattaforme.edilgiulianiback.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Entity
public @Data class SubPrenotazione {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_mezzo")
    private Mezzo mezzo;

    @Temporal(value = TemporalType.DATE)
    private Date lastDay;

    private long margineTrasporto = TimeUnit.HOURS.toMillis(1);

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<BookingDay> giorni = new ArrayList<>();

    public int orelavoro() {
        int h = 0;
        for (BookingDay bd : giorni)
            h+= bd.calcolaOre();
        return h;
    }

    public float calcolaPrezzo() {
        int h;
        float price = 0;
        for (BookingDay bd : giorni){
            h= bd.calcolaOre();
            if (h<=3)
                price+=mezzo.getMinPrice();
            else if (h>=7) {
                price+= mezzo.getDailyPrice();
            }
            else
                price+= h * mezzo.getPrezzo_hour();
        }
        return price;
    }
}
