package com.piattaforme.edilgiulianiback.controllers;


import com.piattaforme.edilgiulianiback.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController{

    @Autowired
    private ClientService clientService;

    @GetMapping("/{productId}")
    public ResponseEntity<String> test(@PathVariable int productId, Authentication connectedUser){
        if (connectedUser==null){
            System.out.println("utente non logged");
            return ResponseEntity.ok("utente non loggato");
        }
        else
            return ResponseEntity.ok(clientService.test(productId, connectedUser.getPrincipal().toString()));
    }


    @PreAuthorize("hasRole('ROLE_user')")
    @GetMapping("/test2/{productId}")
    public ResponseEntity<Integer> testInt(@PathVariable int productId, Authentication connectedUser){
        return ResponseEntity.ok(productId);
    }
}
