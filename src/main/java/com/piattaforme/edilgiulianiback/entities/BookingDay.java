package com.piattaforme.edilgiulianiback.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
public @Data class BookingDay {
    @Id
    @GeneratedValue
    private long id;

    @Temporal(TemporalType.DATE)
    private Date giorno;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Interval> intervalliLavoro;
}
