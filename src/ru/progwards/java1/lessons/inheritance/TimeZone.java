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
        TimeZone k1 = this.getTimeZone();
        TimeZone k2 = time.getTimeZone();
        int sdvigk1, sdvigk2;

        if (k1 == null)
            sdvigk1 = 0;
        else
            sdvigk1 = Math.abs(k1.hours * 60 * 60 + k1.minutes * 60);
        if (k2 == null)
            sdvigk2 = 0;
        else
            sdvigk2 = Math.abs(k2.hours * 60 * 60 + k2.minutes * 60);
        if(zone.hours < 0)
            return super.secondsBetween(time) + Math.abs(sdvigk1 + sdvigk2);
        return super.secondsBetween(time) - Math.abs(sdvigk1 + sdvigk2);
    }

    public static void main(String[] args) {

        ZonedTime zt1 = new ZonedTime(6, 33, 8, new TimeZone(-1));
        ZonedTime zt2 = new ZonedTime(4, 11, 2, new TimeZone(2));
        System.out.println(zt1.secondsBetween(zt2));
        ZonedTime zt3 = new ZonedTime(18, 7, 43, new TimeZone(-2,5));
        Time t4 = new Time(16, 39, 35);
        System.out.println(zt3.secondsBetween(t4));

    }
}

