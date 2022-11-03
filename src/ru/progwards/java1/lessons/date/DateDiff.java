package ru.progwards.java1.lessons.date;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateDiff {
    public static void timeBetween(Date date1, Date date2) {

        System.out.println("Между date1 и date2 " + yearsBetween(date1, date2) + " лет, " + timeBetweenFromMonthToMillis(date1, date2));
    }


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

        /*
        Блок поправки к числу дней, которую робот не учитывает, из-за чего в некоторых случаях в ходе вычисления теряются дни.
        Так происходит, если число в начальной дате больше максимального числа месяца, предшествующего месяцу конечной даты.
        Например, при вычислении интервала между 30 декабря и 5 марта из-за того, что в феврале меньше 30 дней, результат
        выглядит как 2 месяца 5 дней, и он никак не меняется при измении начальной даты в диапазоне от 28 до 31 декабря (для високосного года от 29 до 31)
        Блок поправки, приведённый ниже, эту проблему устраняет, однако с роботом получается расхождение.
         */

//        Calendar dLastClone = (Calendar)dLast.clone();
//        int daysCorrection = 0;
//        dLastClone.add(Calendar.MONTH, -1);
//        if (dFirst.get(Calendar.DAY_OF_MONTH) > dLastClone.getActualMaximum(Calendar.DAY_OF_MONTH)){
//            daysCorrection = dFirst.get(Calendar.DAY_OF_MONTH) - dLastClone.getActualMaximum(Calendar.DAY_OF_MONTH);
//        }
//        System.out.println(daysCorrection);


        while (dFirst.before(dLast)) {
            dFirst.add(Calendar.YEAR, 1);
        }
        dFirst.add(Calendar.YEAR, -1);


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

        return String.format("%s месяцев, %s дней, %s часов, %s минут, %s секунд, %s миллисекунд",
                monthsDiff, daysDiff, hoursDiff, minsDiff, secsDiff, millisDiff);
    }

    public static int yearsBetween(Date date1, Date date2) {
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


    public static void timeToBirthday(Date now, Date birthday) {

        Calendar nowTime = Calendar.getInstance();
        Calendar birthdayTime = Calendar.getInstance();

        nowTime.clear();
        birthdayTime.clear();

        nowTime.setTime(now);
        birthdayTime.setTime(birthday);

        birthdayTime.set(Calendar.YEAR, nowTime.get(Calendar.YEAR));

// если ДР в этом году уже прошёл, то YEAR++ в birthdayTime
        if (birthdayTime.before(nowTime)) {

            birthdayTime.add(Calendar.YEAR, 1);
        }

        Date nextBirthdayTimeDate = new Date(birthdayTime.getTimeInMillis());

        System.out.println("До дня рождения " + timeBetweenFromMonthToMillis(now, nextBirthdayTimeDate));
    }


    public static void averageTime(Date[] events) {

        long averageDiff = 0;

        averageDiff = (events[events.length - 1].getTime() - events[0].getTime()) / (events.length - 1);
        Date firstEvent = new Date(events[0].getTime());
        Date nextEventAverage = new Date(events[0].getTime() + averageDiff);

        System.out.println("Среднее время между событиями " + yearsBetween(firstEvent, nextEventAverage) + " лет, " +
                timeBetweenFromMonthToMillis(firstEvent, nextEventAverage));
    }


    public static void main(String[] args) {

        Date d1 = new Date(2592000000L * 4);
        Date d2 = new Date(2592000000L * 8);
        timeBetween(d1, d2);

        Calendar birthDayExpectancy = Calendar.getInstance();
        birthDayExpectancy.clear();
        birthDayExpectancy.set(2022, Calendar.NOVEMBER, 2, 13, 38, 15);
        Date currentTime = new Date(birthDayExpectancy.getTimeInMillis());

        Calendar myBirthday = Calendar.getInstance();
        myBirthday.clear();
        myBirthday.set(1983, Calendar.JULY, 12, 10, 33, 10);
        Date myBirthdayDate = new Date(myBirthday.getTimeInMillis());

        Calendar test1 = Calendar.getInstance();
        Calendar test2 = Calendar.getInstance();
        test1.set(2049, Calendar.JUNE, 17, 12, 1, 7);
        test2.set(2087, Calendar.OCTOBER, 12, 18, 34, 17);
        Date testDate1 = new Date(test1.getTimeInMillis());
        Date testDate2 = new Date(test2.getTimeInMillis());
        timeBetween(testDate1, testDate2);
        System.out.println();

        timeToBirthday(currentTime, myBirthdayDate);
    }
}
/*Метод, вызванный с параметром, соответствующим 12 июля 1983 года, 10:33:10.654, дата и время выполнения 02 ноября 2022 года, 13:38:15.303 вывел на консоль:
        До дня рождения 8 месяцев, 9 дней, 19 часов, 54 минут, 55 секунд, 351 миллисекунд

        Ожидалось:
        До дня рождения 8 месяцев, 9 дней, 20 часов, 54 минут, 55 секунд, 351 миллисекунд


 */