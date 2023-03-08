package ru.progwards.java1.lessons.datetime;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class Insurance {
    public static enum FormatStyle {SHORT, LONG, FULL}

    private ZonedDateTime start; // - дата-время начала действия страховки.
    private Duration duration;   // - продолжительность действия.

    public Insurance(ZonedDateTime start) {
        this.start = start;
    }

    public Insurance(String strStart, FormatStyle style) {
        DateTimeFormatter dtf;
        switch (style) {
            case SHORT:
                dtf = DateTimeFormatter.ISO_LOCAL_DATE.withZone(ZoneId.systemDefault());
                break;
            case LONG:
                dtf = DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneId.systemDefault());
                break;
            case FULL:
                dtf = DateTimeFormatter.ISO_ZONED_DATE_TIME;
                break;
            default:   // ????????????????
                dtf = DateTimeFormatter.ISO_DATE.withZone(ZoneId.systemDefault());
        }
        TemporalAccessor ta = dtf.parse(strStart);
        this.start = ZonedDateTime.from(ta);
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setDuration(ZonedDateTime expiration) {
        this.duration = Duration.between(start, expiration);
    }

    public void setDuration(int months, int days, int hours) {
        ZonedDateTime expiration = start;
        expiration.plusMonths(months).plusDays(days).plusHours(hours);
        this.duration = Duration.between(start, expiration);
    }

    public void setDuration(String strDuration, FormatStyle style) {
        DateTimeFormatter dtf;
        switch (style) {
            case SHORT:
                this.duration = Duration.ofMillis(Long.parseLong(strDuration));
                break;
            case LONG:
                dtf = DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneId.systemDefault());
                LocalDateTime ldt = LocalDateTime.parse(strDuration, dtf);
                LocalDateTime zero = LocalDateTime.of(0,0,0,0,0);
                this.duration = Duration.between(zero, ldt);
            case FULL:
                this.duration = Duration.parse(strDuration);
        }
    }

    public boolean checkValid(ZonedDateTime dateTime){

        return false;
    }

    public String toString(){

        return null;
    }
}
