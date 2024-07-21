package com.piattaforme.edilgiulianiback.services;

import com.piattaforme.edilgiulianiback.controllers.SubBooking;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PrenotazioneResponse(
        @NotNull
        @NotEmpty
        String nome,
        @NotNull
        @NotEmpty
        String indirizzo,
        @NotNull
        @NotEmpty
        String cap,
        @NotNull
        @NotEmpty
        String descrizione,
        @NotNull
        List<SubBooking> subB,
        @NotNull
        float prezzostimato
){}
