package com.piattaforme.edilgiulianiback.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

enum Ruolo{
    GUEST,CUSTOMER,ADMIN
}

@Entity
public @Data class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private Ruolo role;

    @ManyToMany(mappedBy = "roles")
    private Set<Cliente> users = new HashSet<>();
}
