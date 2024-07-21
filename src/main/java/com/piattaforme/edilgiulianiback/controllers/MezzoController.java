package com.piattaforme.edilgiulianiback.controllers;

import com.piattaforme.edilgiulianiback.entities.Mezzo;
import com.piattaforme.edilgiulianiback.services.MezziService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableWebSecurity
@RequestMapping("mezzi")
public class MezzoController {

    @Autowired
    private MezziService service;

    @PreAuthorize("hasRole('ROLE_user')")
    @GetMapping
    public ResponseEntity<List<Mezzo>> getAllMezzi(Authentication userRequesting){
        return ResponseEntity.ok(service.getAll());
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @PostMapping("/add")
    public ResponseEntity<Long> addMezzo(@RequestParam(name = "mezzo") Mezzo mezzo,Authentication userRequesting){
        return ResponseEntity.ok(service.addMezzo(mezzo));
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @PostMapping("/remove")
    public ResponseEntity<Boolean> removeAvailableMezzo(@RequestParam(name = "mezzoId") long mezzoId,Authentication userRequesting){
        return ResponseEntity.ok(service.removeMezzo(mezzoId));
    }
}
