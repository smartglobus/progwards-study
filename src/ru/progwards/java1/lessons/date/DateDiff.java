package ru.progwards.java1.lessons.date;

import java.util.Calendar;
import java.util.Date;

public class DateDiff {
    public static void timeBetween(Date date1, Date date2) {

        Calendar zeroTime = Calendar.getInstance();
        Calendar timeBtwCal = Calendar.getInstance();

        zeroTime.clear();
        timeBtwCal.clear();
//        d1Cal.clear();
//        d2Cal.clear();
//
////        d1Cal.setTime(date1);
//        d2Cal.setTime(date2);
//
//        d2Cal.set(1980,0,1,0,0,0);
//        System.out.println(d2Cal.getTimeInMillis());

        timeBtwCal.setTimeInMillis(Math.abs(date1.getTime() - date2.getTime()));

        int yearDiff = Math.abs(timeBtwCal.get(Calendar.YEAR) - zeroTime.get(Calendar.YEAR));
        int monthDiff = Math.abs(timeBtwCal.get(Calendar.MONTH) - zeroTime.get(Calendar.MONTH));
        int daysDiff = Math.abs(timeBtwCal.get(Calendar.DAY_OF_MONTH) - zeroTime.get(Calendar.DAY_OF_MONTH));
        int hoursDiff = Math.abs(timeBtwCal.get(Calendar.HOUR) - zeroTime.get(Calendar.HOUR));
        int minutesDiff = Math.abs(timeBtwCal.get(Calendar.MINUTE) - zeroTime.get(Calendar.MINUTE)) + hoursDiff * 60;
        int secondsDiff = Math.abs(timeBtwCal.get(Calendar.SECOND) - zeroTime.get(Calendar.SECOND));
        int millisDiff = Math.abs(timeBtwCal.get(Calendar.MILLISECOND) - zeroTime.get(Calendar.MILLISECOND));

        System.out.println("Между date1 и date2 " + yearDiff + " лет, " + monthDiff + " месяцев, " + daysDiff
                + " дней, " + minutesDiff + " минут, " + secondsDiff + " секунд, " + millisDiff + " миллисекунд");
    }

    public static void main(String[] args) {
        Date d1 = new Date(10800000);
        Date d2 = new Date(31554000000L);
        timeBetween(d1, d2);
    }
}
