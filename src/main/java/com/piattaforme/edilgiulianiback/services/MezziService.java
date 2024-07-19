package com.piattaforme.edilgiulianiback.services;

import com.piattaforme.edilgiulianiback.entities.Mezzo;
import com.piattaforme.edilgiulianiback.repository.RepoMezzi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MezziService {

    @Autowired
    private RepoMezzi repo;

    public List<Mezzo> getAll(){
        return repo.findAllByAvailableTrue();
    }

    public Long addMezzo(Mezzo m){
        return repo.save(m).getId();
    }

    public boolean removeMezzo(long id){
        try{
            Mezzo m = repo.findById(id);
            m.setAvailable(false);
            repo.save(m);
        }catch(Exception e){
            return false;
        }
        return true;
    }
}
