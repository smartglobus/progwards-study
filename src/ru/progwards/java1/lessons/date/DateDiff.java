package ru.progwards.java1.lessons.date;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateDiff {
    public static void timeBetween(Date date1, Date date2) {

        System.out.println("Между date1 и date2 " + yearsBetween(date1, date2) + " лет, " + timeBetweenFromMonthToMillis(date1, date2));
    }


    public static String timeBetweenFromMonthToMillis(Date date1, Date date2) {
        Calendar timeBtwCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        timeBtwCal.clear();

        timeBtwCal.setTimeInMillis(Math.abs(date1.getTime() - date2.getTime()));

        int monthDiff = timeBtwCal.get(Calendar.MONTH);
        int daysDiff = timeBtwCal.get(Calendar.DAY_OF_MONTH) - 1;
        int hoursDiff = timeBtwCal.get(Calendar.HOUR_OF_DAY);
        int minutesDiff = timeBtwCal.get(Calendar.MINUTE);
        int secondsDiff = timeBtwCal.get(Calendar.SECOND);
        int millisDiff = timeBtwCal.get(Calendar.MILLISECOND);


        return monthDiff + " месяцев, " + daysDiff + " дней, " + hoursDiff + " часов, " + minutesDiff + " минут, " +
                secondsDiff + " секунд, " + millisDiff + " миллисекунд";
    }

    public static int yearsBetween(Date date1, Date date2) {
        Calendar timeBtwCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        timeBtwCal.clear();

        timeBtwCal.setTimeInMillis(Math.abs(date1.getTime() - date2.getTime()));

        return timeBtwCal.get(Calendar.YEAR) - 1970;
    }


    public static void timeToBirthday(Date now, Date birthday) {

        Calendar nowTime = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        Calendar birthdayTime = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

        nowTime.clear();
        birthdayTime.clear();

        nowTime.setTime(now);
        birthdayTime.setTime(birthday);

        birthdayTime.set(Calendar.YEAR, nowTime.get(Calendar.YEAR));

// если ДР в этом году уже прошёл, то YEAR++ в birthdayTime
        if (birthdayTime.before(nowTime)) {

            birthdayTime.set(Calendar.YEAR, nowTime.get(Calendar.YEAR) + 1);
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

        Calendar birthDayExpectancy = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        birthDayExpectancy.clear();
        birthDayExpectancy.set(2022, 10, 2, 10, 40, 13);
        Date currentTime = new Date(birthDayExpectancy.getTimeInMillis());

        Calendar myBirthday = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        myBirthday.clear();
        myBirthday.set(1997, 7, 13, 5, 8, 22);
        Date myBirthdayDate = new Date(myBirthday.getTimeInMillis());

        Calendar test1 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        Calendar test2 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        test1.set(2049, 5, 17, 12, 1, 7);
        test2.set(2087, 9, 12, 18, 34, 17);
        Date testDate1 = new Date(test1.getTimeInMillis());
        Date testDate2 = new Date(test2.getTimeInMillis());
        timeBetween(testDate1, testDate2);
        System.out.println();

        timeToBirthday(currentTime, myBirthdayDate);
    }
}
/*
Метод, вызванный с параметром, соответствующим 13 августа 1997 года, 05:08:22.681, дата и время выполнения 02 ноября 2022 года, 10:40:13.156 вывел на консоль:
До дня рождения 2 месяцев, 22 дней, 5 часов, 31 минут, 50 секунд, 475 миллисекунд

Ожидалось:
До дня рождения 9 месяцев, 10 дней, 18 часов, 28 минут, 9 секунд, 525 миллисекунд
 */