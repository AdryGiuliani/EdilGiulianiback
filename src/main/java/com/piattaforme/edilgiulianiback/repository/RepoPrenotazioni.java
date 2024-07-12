package com.piattaforme.edilgiulianiback.repository;

import com.piattaforme.edilgiulianiback.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoPrenotazioni extends JpaRepository<Prenotazione, Long> {
    List<Prenotazione> findAllByOrderByDatainizioDesc();
}
