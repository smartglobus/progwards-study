package ru.progwards.java1.lessons.date;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateDiffAnotherTry {
    public static String timeBetweenFromMonthToMillis(Date date1, Date date2) {
        Calendar dFirst = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        Calendar dLast = Calendar.getInstance(TimeZone.getTimeZone("UTC"));


        if (date2.getTime() > date1.getTime()) {
            dFirst.setTimeInMillis(date1.getTime());
            dLast.setTimeInMillis(date2.getTime());
        } else {
            dFirst.setTimeInMillis(date2.getTime());
            dLast.setTimeInMillis(date1.getTime());
        }

        Calendar dLastClone = (Calendar)dLast.clone();
        int daysCorrection = 0;
        dLastClone.add(Calendar.MONTH, -1);
        if (dFirst.get(Calendar.DAY_OF_MONTH) > dLastClone.getActualMaximum(Calendar.DAY_OF_MONTH)){
            daysCorrection = dFirst.get(Calendar.DAY_OF_MONTH) - dLastClone.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        System.out.println(daysCorrection);

        int yearsDiff = 0;
        while (dFirst.before(dLast)) {
            dFirst.add(Calendar.YEAR, 1);
            yearsDiff++;
        }
        dFirst.add(Calendar.YEAR, -1);
        yearsDiff--;

        int monthsDiff = 0;
        while (dFirst.before(dLast)) {
            dFirst.add(Calendar.MONTH, 1);
            monthsDiff++;
        }
        dFirst.add(Calendar.MONTH, -1);
        monthsDiff--;

        int daysDiff = 0;
        while (dFirst.before(dLast)) {
            dFirst.add(Calendar.DAY_OF_MONTH, 1);
            daysDiff++;
        }
        dFirst.add(Calendar.DAY_OF_MONTH, -1);
        daysDiff--;
//        daysDiff -= daysCorrection;

        long hrsMinsSecsMillis = dLast.getTimeInMillis() - dFirst.getTimeInMillis();

        int hoursDiff = (int) hrsMinsSecsMillis/3600000;
        if (hoursDiff==24){
            hoursDiff=0;
            daysDiff++;
        }
        long minsSecsMillis = hrsMinsSecsMillis%3600000;
        int minsDiff = (int) minsSecsMillis/60000;
        long secsMillis = minsSecsMillis%60000;
        int secsDiff = (int) secsMillis/1000;
        int millisDiff = (int)(secsMillis-secsDiff*1000);
        System.out.println(String.format("Между date1 и date2 %s лет, %s месяцев, %s дней, %s часов, %s минут, %s секунд, %s миллисекунд",
                yearsDiff, monthsDiff, daysDiff, hoursDiff, minsDiff, secsDiff, millisDiff));


        return String.format("%s месяцев, %s дней, %s часов, %s минут, %s секунд, %s миллисекунд",
                monthsDiff, daysDiff, hoursDiff, minsDiff, secsDiff, millisDiff);
    }

    public static int yearsBetween(Date date1, Date date2){
        Calendar dFirst = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        Calendar dLast = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

        if (date2.getTime() > date1.getTime()) {
            dFirst.setTimeInMillis(date1.getTime());
            dLast.setTimeInMillis(date2.getTime());
        } else {
            dFirst.setTimeInMillis(date2.getTime());
            dLast.setTimeInMillis(date1.getTime());
        }

        int yearsDiff = 0;
        while (dFirst.before(dLast)) {
            dFirst.add(Calendar.YEAR, 1);
            yearsDiff++;
        }
        dFirst.add(Calendar.YEAR, -1);
        yearsDiff--;

        return yearsDiff;
    }

    public static void main(String[] args) {
        Calendar test1 = Calendar.getInstance();
        Calendar test2 = Calendar.getInstance();
        test1.clear();
        test2.clear();
        test1.set(1996, Calendar.DECEMBER, 30, 19, 21, 47);
        test2.set(1997, Calendar.MARCH, 5, 19, 21, 47);
        Date testDate1 = new Date(test1.getTimeInMillis());
        Date testDate2 = new Date(test2.getTimeInMillis());

        timeBetweenFromMonthToMillis(testDate1,testDate2);

    }
}
