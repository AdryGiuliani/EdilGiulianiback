package com.piattaforme.edilgiulianiback.services;

import com.piattaforme.edilgiulianiback.controllers.PrenotazioneRequest;
import com.piattaforme.edilgiulianiback.entities.BookingDay;
import com.piattaforme.edilgiulianiback.entities.Interval;
import com.piattaforme.edilgiulianiback.entities.Prenotazione;
import com.piattaforme.edilgiulianiback.entities.SubPrenotazione;
import com.piattaforme.edilgiulianiback.repository.RepoPrenotazioni;
import com.piattaforme.edilgiulianiback.repository.RepoSubPrenotazioni;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PrenotazioniService {

    @Autowired
    private RepoPrenotazioni repo;

    @Autowired
    private RepoSubPrenotazioni repoSub;

    @Autowired
    private PrenotazioniAssembler assembler;

    private long margin;
    private long newMargin;

    @Transactional
    public PrenotazioneResponse addPrenotazione(PrenotazioneRequest pr, boolean isAdmin, String userid) throws ParseException {
        Prenotazione p = assembler.generatePrenotazione(pr, userid);
        if (!validatePrenotazione(p, isAdmin))
            return getErrorResponse();

        return assembler.genPrenotaResponse(repo.save(p));
    }

    public PrenotazioneResponse getErrorResponse(){
        return assembler.genErrorResponse();
    }

    private boolean validatePrenotazione(Prenotazione p, boolean isAdmin) {
        List<SubPrenotazione> subps = p.getSubPrenotazioni();
        if (p.getSubPrenotazioni().isEmpty()) return false;
        for (SubPrenotazione sp : subps ) {
            if (
                    fuoriMargine(sp.getGiorni().getFirst().getIntervalliLavoro().getFirst().getHstart(), isAdmin)
            )
                return false;
        }
        for (SubPrenotazione sp : subps ){
            newMargin = sp.getMargineTrasporto();
            if (hasConflict(sp, getRelevantBooked(sp.getMezzo().getId())))
                return false;
        }
        return true;
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


}
