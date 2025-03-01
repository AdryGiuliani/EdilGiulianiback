package com.piattaforme.edilgiulianiback.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PrenotazioneResp(
        @NotNull
        @NotEmpty
        long id,
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
        @NotEmpty
        List<SubBooking> subB,
        @NotNull
        float prezzostimato,
        @NotNull
        @NotEmpty
        String dataCreazione
){}
