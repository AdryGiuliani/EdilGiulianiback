package com.piattaforme.edilgiulianiback.repository;

import com.piattaforme.edilgiulianiback.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoClienti extends JpaRepository<Cliente,Long> {
    Cliente findByEmail(String email);
    List<Cliente> findAllByNomeOrderByNome(String name);
    Cliente findById(long id);
    boolean existsByEmail(String email);
}
