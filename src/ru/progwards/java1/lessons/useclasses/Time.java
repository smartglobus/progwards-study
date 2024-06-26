package ru.progwards.java1.lessons.useclasses;

public class Time {
    int hours;
    int minutes;
    int seconds;

    public Time(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public String toString() {
        String strHours;
        String strMinutes;
        String strSeconds;

        if (hours >= 0 && hours < 10) strHours = "0" + hours;
        else strHours = Integer.toString(hours);
        if (minutes >= 0 && minutes < 10) strMinutes = "0" + minutes;
        else strMinutes = Integer.toString(minutes);
        if (seconds >= 0 && seconds < 10) strSeconds = "0" + seconds;
        else strSeconds = Integer.toString(seconds);

        return strHours + ":" + strMinutes + ":" + strSeconds;
    }

    public int toSeconds() {
        return hours * 3600 + minutes * 60 + seconds;
    }

    public int secondsBetween(Time time) {
        int diff = Math.abs(time.toSeconds() - this.toSeconds());
        return diff;
    }

    public static void main(String[] args) {
        Time teaTime = new Time(7, 8, 9);
        Time time = new Time(1, 30, 5);
        System.out.println(teaTime.toString());
        System.out.println(time.toSeconds());
        System.out.println(teaTime.toSeconds());
        System.out.println(teaTime.secondsBetween(time));
    }
}
