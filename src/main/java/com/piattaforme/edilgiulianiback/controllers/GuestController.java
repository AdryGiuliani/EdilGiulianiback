package com.piattaforme.edilgiulianiback.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class GuestController {

    @GetMapping("/{name}")
    public String index(@PathVariable String name) {
        System.out.println("printou");
        System.out.println("printou");
        System.out.println("printou");
        return "Hello "+name+" not log in";
    }
}
