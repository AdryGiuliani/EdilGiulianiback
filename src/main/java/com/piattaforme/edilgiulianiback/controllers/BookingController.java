package com.piattaforme.edilgiulianiback.controllers;


import com.piattaforme.edilgiulianiback.services.PrenotazioneResponse;
import com.piattaforme.edilgiulianiback.services.PrenotazioniService;
import com.piattaforme.edilgiulianiback.utils.utility.Utils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

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
        boolean isAdmin = Utils.isAdmin();
        PrenotazioneResponse res;
        try {
            res = service.addPrenotazione(request, isAdmin, userdata.getName());
        }catch (ParseException e){
            return new ResponseEntity<>(service.getErrorResponse(), HttpStatusCode.valueOf(400));
        }
        return ResponseEntity.ok(res);
    }

}
