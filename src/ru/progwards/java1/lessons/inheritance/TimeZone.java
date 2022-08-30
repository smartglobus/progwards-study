package ru.progwards.java1.lessons.inheritance;

public class TimeZone {
    int hours;  //часовой сдвиг, может быть отрицательным
    int minutes;  //сдвиг в минутах

    public TimeZone(int hours) {
        this.hours = hours;
        minutes = 0;
    }

    public TimeZone(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getHours() {
        return hours;
    }
}


class ZonedTime extends Time {
    TimeZone zone;

    public ZonedTime(int hours, int minutes, int seconds) {
        super(hours, minutes, seconds);
        zone = new TimeZone(0, 0);
    }

    public ZonedTime(int hours, int minutes, int seconds, TimeZone zone) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.zone = zone;
    }

    @Override
    public TimeZone getTimeZone() {
        return zone;
    }

    @Override
    public int secondsBetween(Time time) {
        int seconds2;
        int seconds1 = this.toSeconds() + this.getTimeZone().hours * 3600 + this.getTimeZone().minutes * 60;
        if (time.getTimeZone() == null) {
            seconds2 = time.toSeconds();
        }
        seconds2 = time.toSeconds() + time.getTimeZone().hours * 3600 + time.getTimeZone().minutes * 60;

        return Math.abs(seconds1 - seconds2);
    }

    public static void main(String[] args) {
        TimeZone toronto = new TimeZone(-4);
        TimeZone teheran = new TimeZone(4, 30);
        TimeZone london = new TimeZone(0);
        Time nullTimeZone = new Time(12, 40, 25);
        ZonedTime gmtLondon = new ZonedTime(5, 15, 7, london);
        ZonedTime gmt = new ZonedTime(15, 30, 0);
        ZonedTime gmtPlus4_30 = new ZonedTime(15, 30, 45, teheran);
        ZonedTime gmtMinus4 = new ZonedTime(7, 55, 30, toronto);
        System.out.println(gmt.secondsBetween(gmtPlus4_30));
        System.out.println(gmtMinus4.secondsBetween(gmtPlus4_30));
        System.out.println(nullTimeZone.secondsBetween(gmt));
        System.out.println(gmtLondon.secondsBetween(gmtPlus4_30));
    }
}

