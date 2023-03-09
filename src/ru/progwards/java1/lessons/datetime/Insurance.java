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
        setDuration(Duration.ZERO);
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
                dtf = DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.systemDefault());
        }
        TemporalAccessor ta = dtf.parse(strStart);
        this.start = ZonedDateTime.from(ta);
        setDuration(Duration.ZERO);

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
                LocalDateTime zero = LocalDateTime.of(0, 0, 0, 0, 0);
                this.duration = Duration.between(zero, ldt);
            case FULL:
                this.duration = Duration.parse(strDuration);
        }
    }

    //проверить действительна ли страховка на указанную дату-время. Если продолжительность не задана считать страховку бессрочной.
    public boolean checkValid(ZonedDateTime dateTime) {
        if (duration.equals(Duration.ZERO)) return true;
        ZonedDateTime endTime = start.plusHours(duration.toHours());
        if (dateTime.isAfter(endTime)) return false;
        return true;
    }

    // вернуть строку формата "Insurance issued on " + start + validStr, где validStr = " is valid",
    // если страховка действительна на данный момент и " is not valid", если она недействительна.
    public String toString() {
        String validStr = " is not valid";
        if (checkValid(ZonedDateTime.now())) {
            validStr = " is valid";
        }
        return "Insurance issued on " + start + validStr;
    }

    public static void main(String[] args) {
        ZonedDateTime test = ZonedDateTime.from(Instant.now().atZone(ZoneId.systemDefault()));
        Insurance pu = new Insurance(test);
        System.out.println(pu);

    }
}
