package com.piattaforme.edilgiulianiback.repository;

import com.piattaforme.edilgiulianiback.entities.Mezzo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoMezzi extends JpaRepository<Mezzo, Long> {
    Mezzo findByNome(String nome);
    List<Mezzo> findAllByAvailableTrue();
    Mezzo findById(long id);
}
