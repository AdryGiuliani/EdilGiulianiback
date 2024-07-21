package com.piattaforme.edilgiulianiback.services;

import com.nimbusds.jose.shaded.gson.internal.bind.util.ISO8601Utils;
import com.piattaforme.edilgiulianiback.controllers.IsoInterval;
import com.piattaforme.edilgiulianiback.controllers.PrenotazioneRequest;
import com.piattaforme.edilgiulianiback.controllers.SubBooking;
import com.piattaforme.edilgiulianiback.entities.BookingDay;
import com.piattaforme.edilgiulianiback.entities.Interval;
import com.piattaforme.edilgiulianiback.entities.Prenotazione;
import com.piattaforme.edilgiulianiback.entities.SubPrenotazione;
import com.piattaforme.edilgiulianiback.repository.RepoMezzi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class PrenotazioniAssembler {

    @Autowired
    private RepoMezzi repoMezzi;

    public Prenotazione generatePrenotazione(PrenotazioneRequest pr, String userid) throws ParseException {
        Prenotazione p  = new Prenotazione();
        p.setId(pr.id());
        p.setCAP(pr.cap());
        p.setIndirizzo(pr.indirizzo());
        p.setDescrizione(pr.descrizione());
        p.setNome(pr.nome());
        p.setClienteID(userid);
        p.setDataCreazione(new Date());
        p.setSubPrenotazioni(convertSubP(pr.subB(), calcolaMargineTrasporto(pr.indirizzo(),pr.cap())));
        p.calcolaPrezzo();

        return p;
    }

    private long calcolaMargineTrasporto(String indirizzo, String cap) {
        //placeholder
        return TimeUnit.HOURS.toMillis(1);
    }

    private List<SubPrenotazione> convertSubP(List<SubBooking> subBookings, long trasporto) throws ParseException {
        ArrayList<SubPrenotazione> res =new ArrayList<>();
        for (SubBooking sub : subBookings){
            SubPrenotazione sp = new SubPrenotazione();
            sp.setMargineTrasporto(trasporto);
            sp.setMezzo(repoMezzi.findById(sub.getIdmezzo()));
            sp.setGiorni(convertToBookingDays(sub.getIntervals()));
            sp.setLastDay(sp.getGiorni().getLast().getGiorno());
        }
        return res;
    }

    private List<BookingDay> convertToBookingDays(List<IsoInterval> intervals) throws ParseException {
        ArrayList<BookingDay> res = new ArrayList<>();

        Map<Date, ArrayList<Interval>> ivals = new HashMap<>();
        for (IsoInterval iso : intervals){
            Interval i = isoToInterval(iso);
            Date day = i.getDay();
            ArrayList<Interval> l = new ArrayList<>();
            if (!ivals.containsKey(day)){
                l.add(i);
                ivals.put(day, l);
            }
            else{
                l = ivals.get(day);
                l.add(i);
            }
        }

        for (Date day : ivals.keySet()){
            BookingDay b = new BookingDay();
            b.setGiorno(day);
            ivals.get(day).sort(null);
            b.setIntervalliLavoro(ivals.get(day));
            res.add(b);
        }

        return res;
    }

    private Interval isoToInterval(IsoInterval iso) throws ParseException {
        Date start = ISO8601Utils.parse(iso.getStart(),new ParsePosition(0));
        Date end = ISO8601Utils.parse(iso.getStart(), new ParsePosition(0));
        if (start.after(end) || start.equals(end)) throw new ParseException("Orari non corretti", 0);
        Calendar cstart = Calendar.getInstance();
        cstart.setTime(start);
        Calendar cend = Calendar.getInstance();
        cend.setTime(end);
        if (cstart.get(Calendar.HOUR_OF_DAY) < 7 || cstart.get(Calendar.HOUR_OF_DAY)>= 16
        || cend.get(Calendar.HOUR_OF_DAY) < 7 || cend.get(Calendar.HOUR_OF_DAY)>= 16 ||
                cstart.get(Calendar.YEAR) != cend.get(Calendar.YEAR) || cstart.get(Calendar.DAY_OF_YEAR) != cend.get(Calendar.DAY_OF_YEAR)||
        cstart.get(Calendar.DAY_OF_WEEK) >= 6 ) throw new ParseException("Orari non corretti", 0);

        cstart.set(Calendar.MINUTE, 0);
        cstart.set(Calendar.SECOND, 0);
        cstart.set(Calendar.MILLISECOND, 0);

        cend.set(Calendar.MINUTE, 0);
        cend.set(Calendar.SECOND, 0);
        cend.set(Calendar.MILLISECOND, 0);

        Interval i  =new Interval();
        i.setHstart(cstart.getTime());
        i.setHfine(cend.getTime());

        return i;
    }

    public PrenotazioneResponse genErrorResponse() {
        return  new PrenotazioneResponse("Err", "0", "0", "0", new ArrayList<>(), 0);
    }

    public PrenotazioneResponse genPrenotaResponse(Prenotazione save) {
        return new PrenotazioneResponse(
                save.getNome(),
                save.getIndirizzo(),
                save.getCAP(),
                save.getDescrizione(),
                toSubBooking(save.getSubPrenotazioni()),
                save.getPrezzoStimato()
        );
    }

    private List<SubBooking> toSubBooking(List<SubPrenotazione> subPrenotazioni) {
        List<SubBooking> res = new ArrayList<>();
        for (SubPrenotazione sp : subPrenotazioni){
            SubBooking sb = new SubBooking();
            sb.setIdmezzo(sp.getMezzo().getId());
            sb.setIntervals(toIsoIntervals(sp.getGiorni()));
        }
        return res;
    }

    private List<IsoInterval> toIsoIntervals(List<BookingDay> giorni) {
        List<IsoInterval> res = new ArrayList<>();
        for (BookingDay d : giorni){
            for (Interval i : d.getIntervalliLavoro()){
                IsoInterval iso = new IsoInterval();
                iso.setStart(ISO8601Utils.format(i.getHstart()));
                iso.setEnd(ISO8601Utils.format(i.getHfine()));
            }
        }
        return res;
    }
}
