package com.piattaforme.edilgiulianiback.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
public @Data class BookingDay implements Comparable<BookingDay>{
    @Id
    @GeneratedValue
    private long id;

    @Temporal(TemporalType.DATE)
    private Date giorno;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Interval> intervalliLavoro;

    @ManyToOne
    @JoinColumn(name = "Subp_id")
    private SubPrenotazione subp;

    @Override
    public int compareTo(BookingDay o) {
        if (this.equals(o)) return 0;
        if (this.getGiorno().before(o.getGiorno())) return 1;
        return -1;
    }

    public int calcolaOre() {
        int htot = 0;
        for (Interval i : intervalliLavoro) {
            htot += i.getHours();
        }
        return htot;
    }
}
