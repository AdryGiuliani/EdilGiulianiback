package com.piattaforme.edilgiulianiback.controllers;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public @Data class SubBooking {
    private long idmezzo;
    private List<IsoInterval> intervals=  new ArrayList<>();
}
