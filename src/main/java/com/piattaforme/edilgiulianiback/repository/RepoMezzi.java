package com.piattaforme.edilgiulianiback.repository;

import com.piattaforme.edilgiulianiback.entities.Mezzo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoMezzi extends JpaRepository<Mezzo, Long> {
    Mezzo findByNome(String nome);
    Mezzo findById(long id);
}
