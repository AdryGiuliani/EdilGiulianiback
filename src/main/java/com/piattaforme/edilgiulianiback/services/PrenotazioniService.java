package com.piattaforme.edilgiulianiback.services;

import com.nimbusds.jose.shaded.gson.internal.bind.util.ISO8601Utils;
import com.piattaforme.edilgiulianiback.dtos.IsoInterval;
import com.piattaforme.edilgiulianiback.dtos.PrenotazioneRequest;
import com.piattaforme.edilgiulianiback.dtos.PrenotazioneResp;
import com.piattaforme.edilgiulianiback.entities.BookingDay;
import com.piattaforme.edilgiulianiback.entities.Interval;
import com.piattaforme.edilgiulianiback.entities.Prenotazione;
import com.piattaforme.edilgiulianiback.entities.SubPrenotazione;
import com.piattaforme.edilgiulianiback.repository.RepoBookingDay;
import com.piattaforme.edilgiulianiback.repository.RepoPrenotazioni;
import com.piattaforme.edilgiulianiback.repository.RepoSubPrenotazioni;
import com.piattaforme.edilgiulianiback.utils.utility.IsoIntervalConverter;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class PrenotazioniService {

    @Autowired
    private RepoPrenotazioni repo;

    @Autowired
    private RepoSubPrenotazioni repoSub;

    @Autowired
    private RepoBookingDay repobDay;

    @Autowired
    private PrenotazioniAssembler assembler;

    private long margin;
    private long newMargin;

    @Transactional
    public PrenotazioneResp addPrenotazione(PrenotazioneRequest pr, boolean isAdmin, String userid) throws ParseException {
        Prenotazione p = assembler.generatePrenotazione(pr, userid);
        String errmex = validatePrenotazione(p, isAdmin);
        if (!errmex.isEmpty())
            return getErrorResponse(errmex);
        long id =repo.save(p).getId();
        p.setId(id);
        return assembler.genPrenotaResponse(p);
    }

    public PrenotazioneResp getErrorResponse(String mex){
        return assembler.genErrorResponse(mex);
    }

    private String validatePrenotazione(Prenotazione p, boolean isAdmin) {
        List<SubPrenotazione> subps = p.getSubPrenotazioni();
        if (subps.isEmpty()) return "nessuna data/ora selezionata";
        for (SubPrenotazione sp : subps ) {
            if (
                    fuoriMargine(sp.getGiorni().getFirst().getIntervalliLavoro().getFirst().getHstart(), isAdmin)
            )
                return "prenotazione non confermata: hai tempo fino al giorno lavorativo precedente h16:00";
        }
        for (SubPrenotazione sp : subps ){
            newMargin = sp.getMargineTrasporto();
            if (hasConflict(sp, getRelevantBooked(sp.getMezzo().getId())))
                return "OPS qualcuno ha prenotato nelle date da te indicate";
        }
        return "";
    }

    private boolean fuoriMargine(Date dbooking, boolean isAdmin) {
        if (isAdmin) return false;
        Date now = new Date();

        // Create a calendar instance with the given date
        Calendar givenCalendar = Calendar.getInstance();
        givenCalendar.setTime(dbooking);

        // Subtract one day from the given date
        givenCalendar.add(Calendar.DAY_OF_YEAR, -1);

        // Set the time to 16:00 (4 PM)
        givenCalendar.set(Calendar.HOUR_OF_DAY, 16);
        givenCalendar.set(Calendar.MINUTE, 0);
        givenCalendar.set(Calendar.SECOND, 0);
        givenCalendar.set(Calendar.MILLISECOND, 0);

        // Get the calculated date-time
        Date dayBeforeGivenDateAt16 = givenCalendar.getTime();

        // Check if the current date and time is before the calculated date-time
        return now.after(dayBeforeGivenDateAt16);
    }

    @Transactional(readOnly = true)
    public List<SubPrenotazione> getRelevantBooked(long mezzoId){
        return repoSub.findAllByMezzo_IdAndLastDayAfter(mezzoId, new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(15)));
    }

    private boolean hasConflict(SubPrenotazione newsp, List<SubPrenotazione> list ){
        for (SubPrenotazione sp : list){
            //non verifica il conflitto con le sue stesse date
            margin = sp.getMargineTrasporto();
            if (newsp.getGiorni().isEmpty()) return true;
            if ( sp.getId()!=newsp.getId() && hasDailyConflict(newsp.getGiorni(), sp.getGiorni()))
                return true;
        }
        return false;
    }

    private boolean hasDailyConflict(List<BookingDay> lb1, List<BookingDay> lb2){
        for (BookingDay bd1 : lb1)
            for (BookingDay bd2: lb2)
                if (bd1.getGiorno().equals(bd2.getGiorno())){
                    if (hasIntervalConflict(bd1.getIntervalliLavoro(), bd2.getIntervalliLavoro()))
                        return true;
                }
        return false;
    }

    private boolean hasIntervalConflict(List<Interval> li1, List<Interval> li2){
        if (li1.isEmpty()) return true;
        for (Interval i1: li1)
            for (Interval i2 : li2)
                if (i1.getHstart().after(i1.getHfine()) || i1.haveConflict(i2, newMargin, margin))
                    return true;
        return false;
    }


    public List<IsoInterval> getPrenotazioniMezzoHours(long idmezzo, Date day) {
        val res = new ArrayList<IsoInterval>();
        val l = repobDay.findAllByIdMezzoAndDay(idmezzo, day);
        l.forEach(
                (BookingDay bd) -> bd.getIntervalliLavoro().forEach(
                    (Interval i) -> res.add(IsoIntervalConverter.toIsoInterval(i))
                )
        );
        return res;
    }

    @Transactional
    public List<String> getPrenotazioniMezzoDaily(long idmezzo) {
        val l = getRelevantBooked(idmezzo);
        val res = new HashMap<String,Boolean>();
        for (val sb : l){
            for (val day : sb.getGiorni()){
                res.putIfAbsent(ISO8601Utils.format(day.getGiorno(), false),true);
            }
        }
        return res.keySet().stream().toList();
    }

    @Transactional(readOnly = true)
    public List<PrenotazioneResp> getMyBookings(String name) {
        val res = new ArrayList<PrenotazioneResp>();
        repo.findAllByClienteIDOrderByDataCreazioneDesc(name).forEach(
                prenotazione -> {
                    res.add(assembler.genPrenotaResponse(prenotazione));
                }
        );
        return res;
    }



    @Transactional
    public String deleteP(Long id, String name, boolean admin) {
        Prenotazione p = repo.findByIdAndClienteID(id, name);
        if (p == null)
            return "Prenotazione non trovata, prova ad aggiornare la pagina";
        String err = validatePrenotazione(p, admin);
        if (!err.isEmpty())
            return err;
        if (admin)
            repo.deleteById(id);
        else
            repo.deleteByIdAndClienteID(id,name);
        return "ok";
    }
}
