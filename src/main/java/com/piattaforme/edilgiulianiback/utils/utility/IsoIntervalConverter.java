package com.piattaforme.edilgiulianiback.utils.utility;

import com.nimbusds.jose.shaded.gson.internal.bind.util.ISO8601Utils;
import com.piattaforme.edilgiulianiback.controllers.IsoInterval;
import com.piattaforme.edilgiulianiback.entities.BookingDay;
import com.piattaforme.edilgiulianiback.entities.Interval;

import java.text.ParseException;
import java.text.ParsePosition;

public class IsoIntervalConverter {

    public static Interval toInterval(IsoInterval iso) throws ParseException {
        Interval res = new Interval();
        res.setHstart(ISO8601Utils.parse(iso.getStart(), new ParsePosition(0)));
        res.setHfine(ISO8601Utils.parse(iso.getEnd(), new ParsePosition(0)));
        return res;
    }

    public static IsoInterval toIsoInterval(Interval i) {
        IsoInterval res = new IsoInterval();
        res.setStart(ISO8601Utils.format(i.getHstart(), false));
        res.setEnd(ISO8601Utils.format(i.getHfine(), false));
        return res;
    }

}
