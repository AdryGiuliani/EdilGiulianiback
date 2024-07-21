package com.piattaforme.edilgiulianiback.controllers;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PrenotazioneRequest(
    Long id,
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
    List<SubBooking> subB,
    @NotNull
    @NotEmpty
    String descrizione
    ){}

