package com.piattaforme.edilgiulianiback.entities;


import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Embeddable
public @Data class Interval implements  Comparable<Interval> {
    @Temporal(TemporalType.TIMESTAMP)
    private Date hstart;
    @Temporal(TemporalType.TIMESTAMP)
    private Date hfine;

    public boolean haveConflict(Interval i2, long margin1, long margin2) {
        Date newEnd2 = new Date(i2.hfine.getTime() + margin2);
        Date newEnd1 = new Date(hfine.getTime() + margin1);
        return (hstart.equals(i2.hstart) || newEnd1.equals(newEnd2)) ||
                (hstart.after(i2.hstart) && hstart.before(newEnd2)) ||
                (newEnd1.after(i2.hstart) && hfine.before(newEnd2));
    }


    @Override
    public int compareTo(Interval o) {
        if (this.equals(o)) return 0;
        if (this.hstart.before(o.hstart)) return +1;
        return -1;
    }

    public int getHours() {
        int diff = (int) Math.abs((hfine.getTime()-hstart.getTime())/ TimeUnit.HOURS.toMillis(1));
        Calendar c = Calendar.getInstance();
        c.setTime(hstart);

        c.set(Calendar.HOUR_OF_DAY, 12);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date lunchstart = c.getTime();
        c.set(Calendar.HOUR_OF_DAY, 13);
        Date lunchend = c.getTime();
        if ((hstart.before(lunchstart)||hstart.equals(lunchstart)) && (hfine.after(lunchend)||hfine.equals(lunchend)))
            diff--;
        return diff;
    }
}
