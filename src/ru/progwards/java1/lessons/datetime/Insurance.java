package ru.progwards.java1.lessons.datetime;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class Insurance {
    public enum FormatStyle {SHORT, LONG, FULL}
    private ZonedDateTime start; // - дата-время начала действия страховки.
    private Duration duration;   // - продолжительность действия.
    private ZonedDateTime dateTime = ZonedDateTime.now();

    public Insurance(ZonedDateTime start) {
        this.start = start;
        setDuration(Duration.ZERO);
    }

    public Insurance(String strStart, FormatStyle style) {
        DateTimeFormatter dtf;
        switch (style) {
            case SHORT:
                dtf = DateTimeFormatter.ISO_LOCAL_DATE;
                this.start = ZonedDateTime.of(LocalDate.parse(strStart, dtf), LocalTime.MIDNIGHT, ZoneId.systemDefault());
                break;
            case LONG:
                dtf = DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneId.systemDefault());
                System.out.println(dtf);
                this.start = ZonedDateTime.parse(strStart, dtf);
                break;
            default: // case: FULL
                dtf = DateTimeFormatter.ISO_ZONED_DATE_TIME;
                this.start = ZonedDateTime.parse(strStart, dtf);
        }
        setDuration(Duration.ZERO);
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setDuration(ZonedDateTime expiration) {
        this.duration = Duration.between(start, expiration);
    }

    public void setDuration(int months, int days, int hours) {
        // !!! т.к. объекты пакета time immutable, методы типа .plus...(...) возвращают измененную копию объекта start, не изменяя его самого
        ZonedDateTime expiration = start.plusMonths(months).plusDays(days).plusHours(hours);
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
                LocalDateTime zero = LocalDateTime.of(0, 1, 1, 0, 0).minusMonths(1).minusDays(1);// есть ли другие варианты?..
                this.duration = Duration.between(zero, ldt);
                break;
            default:
                this.duration = Duration.parse(strDuration);
        }
    }

    //проверить действительна ли страховка на указанную дату-время. Если продолжительность не задана считать страховку бессрочной.
    public boolean checkValid(ZonedDateTime dateTime) {
        if (duration.equals(Duration.ZERO) && dateTime.isAfter(start)) return true;
        ZonedDateTime endTime = start.plusHours(duration.toHours());
        return dateTime.isAfter(start) && dateTime.isBefore(endTime);
    }

    // вернуть строку формата "Insurance issued on " + start + validStr, где validStr = " is valid",
    // если страховка действительна на данный момент и " is not valid", если она недействительна.
    public String toString() {
        String validStr = " is not valid";
        if (checkValid(dateTime)) {
            validStr = " is valid";
        }
        return "Insurance issued on " + start + validStr;
    }

    public static void main(String[] args) {
        //TemporalAccessor: {},ISO,Europe/Moscow resolved to 2023-03-08 of type java.time.format.Parsed
        TemporalAccessor ta = DateTimeFormatter.ISO_ZONED_DATE_TIME.parse("2023-03-12T18:06:11.940343+03:00[Europe/Moscow]");
//        ZonedDateTime test = ZonedDateTime.from(Instant.now().atZone(ZoneId.systemDefault()));
//        ZonedDateTime test2 = ZonedDateTime.of(2023, 3, 9, 1, 0, 0, 0, ZoneId.systemDefault());
//        Insurance pu = new Insurance("2023-03-14T08:36:12.899241+03:00[Europe/Moscow]", FormatStyle.FULL);
        Insurance fromTA = new Insurance(ZonedDateTime.from(ta));
        fromTA.setDuration(0, 0, 1);
//        System.out.println(pu.dateTime);
        System.out.println(fromTA);
        System.out.println();
    }
}
