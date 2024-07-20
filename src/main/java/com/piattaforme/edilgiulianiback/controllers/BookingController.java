package com.piattaforme.edilgiulianiback.controllers;


import com.piattaforme.edilgiulianiback.services.PrenotazioniService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booking")
@EnableWebSecurity
public class BookingController {

    @Autowired
    private PrenotazioniService service;

    @PostMapping("/new")
    @PreAuthorize("hasRole('ROLE_user')")
    public ResponseEntity<PrenotazioneResponse> prenota(
            @Valid @RequestBody PrenotazioneRequest request,
            Authentication userdata
    ){
        boolean isAdmin = false;
        for (GrantedAuthority sa : userdata.getAuthorities())
            if (sa.getAuthority().equals("ROLE_admin")){
                isAdmin =true;
                break;
            }
        return ResponseEntity.ok(service.addPrenotazione(request, isAdmin, userdata.getName()));
    }

}
