package com.piattaforme.edilgiulianiback.controllers;

import com.piattaforme.edilgiulianiback.entities.Mezzo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public @Data class SubBooking {
    private Mezzo mezzo;
    private List<IsoInterval> intervals=  new ArrayList<>();
}
