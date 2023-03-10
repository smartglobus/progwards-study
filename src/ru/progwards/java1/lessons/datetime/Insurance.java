package ru.progwards.java1.lessons.datetime;

import javax.print.attribute.standard.MediaSize;
import java.time.*;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.ResolverStyle;
import java.time.temporal.TemporalAccessor;

public class Insurance {
    public enum FormatStyle {SHORT, LONG, FULL}

    private ZonedDateTime start; // - дата-время начала действия страховки.
    private Duration duration;   // - продолжительность действия.
    ZonedDateTime dateTime;

    public Insurance(ZonedDateTime start) {
        this.start = start;
        setDuration(Duration.ZERO);
    }

    public Insurance(String strStart, FormatStyle style) {
        DateTimeFormatter dtf;
        switch (style) {
            case SHORT:
                dtf = DateTimeFormatter.ISO_LOCAL_DATE;//.withZone(ZoneId.systemDefault());
//                dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'");
                System.out.println(dtf.format(dtf.parse(strStart)));

                this.start = ZonedDateTime.of(LocalDate.parse(strStart, dtf), LocalTime.MIDNIGHT, ZoneId.systemDefault());
                break;
            case LONG:
                dtf = DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneId.systemDefault());
                System.out.println(dtf);
                this.start = ZonedDateTime.parse(strStart, dtf);
                break;
            default:
                dtf = DateTimeFormatter.ISO_ZONED_DATE_TIME;
                this.start = ZonedDateTime.parse(strStart, dtf);
//            default:   // ????????????????
//                dtf = DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.systemDefault());
        }
//        TemporalAccessor ta = dtf.parse(strStart);
//        this.start = ZonedDateTime.parse(strStart,dtf);
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
                LocalDateTime zero = LocalDateTime.of(0, 1, 1, 0, 0);
                this.duration = Duration.between(zero, ldt);
//                ldt.getLong();
//                        Duration.
                break;
            default:
                this.duration = Duration.parse(strDuration);
        }
    }

    //проверить действительна ли страховка на указанную дату-время. Если продолжительность не задана считать страховку бессрочной.
    public boolean checkValid(ZonedDateTime dateTime) {
        if (duration.equals(Duration.ZERO)) return true;
        ZonedDateTime endTime = start.plusHours(duration.toHours());
        return dateTime.isAfter(start) && dateTime.isBefore(endTime);
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
        //TemporalAccessor: {},ISO,Europe/Moscow resolved to 2023-03-08 of type java.time.format.Parsed
        TemporalAccessor ta = DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneId.systemDefault()).parse("2011-12-03T10:15:30");
        ZonedDateTime test = ZonedDateTime.from(Instant.now().atZone(ZoneId.systemDefault()));
        ZonedDateTime test2 = ZonedDateTime.of(2023, 3, 9, 1, 0, 0, 0, ZoneId.systemDefault());
        Insurance pu = new Insurance("2011-12-03", FormatStyle.SHORT);
//        Insurance fromTA = new Insurance(ZonedDateTime.from(ta));
        System.out.println(pu);
        System.out.println();
    }
}
