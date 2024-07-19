package com.piattaforme.edilgiulianiback.services;

import com.piattaforme.edilgiulianiback.entities.Prenotazione;
import com.piattaforme.edilgiulianiback.entities.SubPrenotazione;
import com.piattaforme.edilgiulianiback.repository.RepoPrenotazioni;
import jakarta.persistence.LockModeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class PrenotazioniService {

    @Autowired
    private RepoPrenotazioni repo;

    public boolean addPrenotazione(Prenotazione p, boolean isAdmin){
        if (!validatePrenotazione(p, isAdmin))
            return false;
        repo.save(p);
        return true;
    }

    private boolean validatePrenotazione(Prenotazione p, boolean isAdmin) {

        List<SubPrenotazione> subp = p.getSubPrenotazioni();
        if (
                !entroMargine(subp.getFirst().getGiorni().getFirst().getGiorno(), isAdmin) ||
                        entroMargine(new Date(), false)
        )
            return false;
        return true;
    }

    private boolean entroMargine(Date dbooking, boolean isAdmin){
        Date margin = new Date();
        if (!isAdmin) margin= new Date(margin.getTime()+TimeUnit.HOURS.toMillis(15));
        return dbooking.after(margin);
    }

}
