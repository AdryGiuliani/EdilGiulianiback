package com.piattaforme.edilgiulianiback.controllers;


import com.piattaforme.edilgiulianiback.dtos.IsoInterval;
import com.piattaforme.edilgiulianiback.dtos.MexWrapper;
import com.piattaforme.edilgiulianiback.dtos.PrenotazioneRequest;
import com.piattaforme.edilgiulianiback.dtos.PrenotazioneResp;
import com.piattaforme.edilgiulianiback.services.PrenotazioniService;
import com.piattaforme.edilgiulianiback.utils.utility.Utils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/booking")
@EnableWebSecurity
public class BookingController {

    @Autowired
    private PrenotazioniService service;

    @PostMapping("/new")
    @PreAuthorize("hasRole('ROLE_user')")
    public ResponseEntity<PrenotazioneResp> prenota(
            @Valid @RequestBody PrenotazioneRequest request,
            Authentication userdata
    ){
        boolean isAdmin = Utils.isAdmin();
        PrenotazioneResp res;
        try {
            res = service.addPrenotazione(request, isAdmin, userdata.getName());

        }catch (ParseException e){
            return ResponseEntity.ok(service.getErrorResponse(e.getMessage()));
        }
//        if (res.nome().equalsIgnoreCase("err"))
//            return new ResponseEntity<>(res, HttpStatusCode.valueOf(400));
        return ResponseEntity.ok(res);
    }

    @GetMapping("/alldays")
    @PreAuthorize("hasRole('ROLE_user')")
    public ResponseEntity<List<String>> getBookedDays(
            @RequestParam(name = "idmezzo") long idmezzo,
            Authentication userdata
    ){
        return ResponseEntity.ok(service.getPrenotazioniMezzoDaily(idmezzo));
    }

    @GetMapping("/allhours")
    @PreAuthorize("hasRole('ROLE_user')")
    public ResponseEntity<List<IsoInterval>> getBookedHours(
            @RequestParam(name = "idmezzo") long idmezzo,
            @RequestParam(name = "day") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date day,
            Authentication userdata
    ){
        return ResponseEntity.ok(service.getPrenotazioniMezzoHours(idmezzo,day));
    }


    @GetMapping("/mybookings")
    @PreAuthorize("hasRole('ROLE_user')")
    public ResponseEntity<List<PrenotazioneResp>> getMyBookings(
            Authentication userdata
    ){
        return ResponseEntity.ok(service.getMyBookings(userdata.getName()));
    }

    @PostMapping("/deleteP")
    @PreAuthorize("hasRole('ROLE_user')")
    public ResponseEntity<MexWrapper> deleteP(
            @RequestParam(name = "idp") long idp,
            Authentication userdata){
        return ResponseEntity.ok(new MexWrapper(service.deleteP(idp,userdata.getName(), Utils.isAdmin())));
    }

}
