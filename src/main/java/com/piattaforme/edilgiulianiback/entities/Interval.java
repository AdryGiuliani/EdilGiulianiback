package com.piattaforme.edilgiulianiback.entities;


import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;

@Embeddable
public @Data class Interval {

    @Temporal(TemporalType.TIME)
    private Date hstart;

    @Temporal(TemporalType.TIME)
    private Date hfine;

}
