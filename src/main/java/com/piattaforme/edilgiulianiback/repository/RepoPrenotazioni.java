package com.piattaforme.edilgiulianiback.repository;

import com.piattaforme.edilgiulianiback.entities.Prenotazione;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RepoPrenotazioni extends CrudRepository<Prenotazione, Long> {
    List<Prenotazione> findAllByOrderByDatainizioDesc();
}
