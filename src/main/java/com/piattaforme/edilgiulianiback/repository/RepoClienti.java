package com.piattaforme.edilgiulianiback.repository;

import com.piattaforme.edilgiulianiback.entities.Cliente;
import com.piattaforme.edilgiulianiback.entities.Prenotazione;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RepoClienti extends CrudRepository<Cliente,Long> {
    Cliente findByEmail(String email);
    List<Cliente> findAllByNomeOrderByNome(String name);
    Cliente findById(long id);
    void updateClienteById(long id, Cliente cliente);
}
