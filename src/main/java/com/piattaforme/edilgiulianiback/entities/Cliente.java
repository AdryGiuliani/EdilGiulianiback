package com.piattaforme.edilgiulianiback.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@Entity
public @Data class Cliente {
    @Id
    private long id;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> roles = new HashSet<>();

    @Column(nullable = false, unique = true)
    private String email;
    private String tel;
    private String nome;
    private String cognome;
    private boolean isAzienda=false;
    private String cf;
    private String piva;
    private String codUnivoco;

    @OneToMany(mappedBy = "cliente_p", cascade = CascadeType.ALL)
    private List<Prenotazione> prenotazioni = new ArrayList<>();
    @OneToMany(mappedBy = "cliente_c", cascade = CascadeType.ALL)
    private List<Preventivo> preventivi = new ArrayList<>();

}
