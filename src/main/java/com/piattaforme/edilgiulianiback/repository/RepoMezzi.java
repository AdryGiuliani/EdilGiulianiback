package com.piattaforme.edilgiulianiback.repository;

import com.piattaforme.edilgiulianiback.entities.Mezzo;
import org.springframework.data.repository.CrudRepository;

public interface RepoMezzi extends CrudRepository<Mezzo, Long> {
    Mezzo findByNome(String nome);
    Mezzo findById(long id);
    void updateMezzoById(long id, Mezzo mezzo);
}
