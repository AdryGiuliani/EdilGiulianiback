package com.piattaforme.edilgiulianiback.repository;

import com.piattaforme.edilgiulianiback.entities.Prenotazione;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoPrenotazioni extends JpaRepository<Prenotazione, Long> {
    List<Prenotazione> findAllByOrderByDataCreazioneDesc();
    List<Prenotazione> findAllByClienteIDOrderByDataCreazioneDesc(String id);
    void deleteByIdAndClienteID(long id, String clientId);
    Prenotazione findByIdAndClienteID(long id, String clientId);
    Prenotazione findById(long id);
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Override
    <S extends Prenotazione> S save(S entity);
}