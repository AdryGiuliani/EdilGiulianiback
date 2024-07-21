package com.piattaforme.edilgiulianiback.repository;

import com.piattaforme.edilgiulianiback.entities.SubPrenotazione;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface RepoSubPrenotazioni extends JpaRepository<SubPrenotazione, Long> {

    @Lock(value = LockModeType.PESSIMISTIC_READ)
    List<SubPrenotazione> findAllByMezzo_IdAndLastDayAfter(long id, Date date);
    
}
