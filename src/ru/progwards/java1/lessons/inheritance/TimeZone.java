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
        int seconds1;
        int seconds2;

        if (this.getTimeZone() == null) {
            seconds1 = this.toSeconds();
        } else {
            if (this.getTimeZone().hours < 0){
                seconds1 = this.getTimeZone().hours * 3600 - this.getTimeZone().minutes * 60 + this.toSeconds();
            }else {
                seconds1 = this.getTimeZone().hours * 3600 + this.getTimeZone().minutes * 60 + this.toSeconds();
            }

        }

        if (time.getTimeZone() == null) {
            seconds2 = time.toSeconds();
        } else {
            if(time.getTimeZone().hours < 0){
                seconds2 = time.getTimeZone().hours * 3600 - time.getTimeZone().minutes * 60 + time.toSeconds();
            }else {
                seconds2 = time.getTimeZone().hours * 3600 + time.getTimeZone().minutes * 60 + time.toSeconds();
            }

        }

        return Math.abs(seconds1 - seconds2);
    }

    public static void main(String[] args) {
//        TimeZone toronto = new TimeZone(-4);
//        TimeZone teheran = new TimeZone(4, 30);
//        TimeZone london = new TimeZone(0);
//        Time nullTimeZone = new Time(12, 40, 25);
//        ZonedTime gmtLondon = new ZonedTime(5, 15, 7, london);
//        ZonedTime gmt = new ZonedTime(15, 30, 0);
//        ZonedTime gmtPlus4_30 = new ZonedTime(15, 30, 45, teheran);
//        ZonedTime gmtMinus4 = new ZonedTime(7, 55, 30, toronto);
        ZonedTime zt1 = new ZonedTime(6, 33, 8, new TimeZone(-1));
        ZonedTime zt2 = new ZonedTime(4, 11, 2, new TimeZone(2));
        Time teaTime = new Time(7, 8, 9);
        System.out.println(zt1.secondsBetween(zt2));
//        System.out.println(gmt.secondsBetween(gmtPlus4_30));
//        System.out.println(gmtMinus4.secondsBetween(gmtPlus4_30));
//        System.out.println(nullTimeZone.secondsBetween(gmt));
//        System.out.println(gmtLondon.secondsBetween(gmtPlus4_30));
    }
}

