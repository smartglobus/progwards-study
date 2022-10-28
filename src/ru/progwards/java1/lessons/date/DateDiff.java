package ru.progwards.java1.lessons.date;

import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateDiff {
    public static void timeBetween(Date date1, Date date2) {

        System.out.println("Между date1 и date2 " + yearsBetween(date1, date2) + " лет, " + timeBetweenFromMonthToMillis(date1, date2));
    }


    public static String timeBetweenFromMonthToMillis(Date date1, Date date2) {
        Calendar d1 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        Calendar d2 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        d1.setTime(date1);
        d2.setTime(date2);
        Calendar epochTime = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        Calendar timeBtwCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        timeBtwCal.clear();
        epochTime.clear();
        timeBtwCal.setTimeInMillis(Math.abs(date1.getTime() - date2.getTime()));

//        int daysCorrNumber = dayCorrection(d1, d2, epochTime, timeBtwCal);
//        System.out.println("daysCorrNumber = " + daysCorrNumber);

//        System.out.println("день месяца  = "+daysDifference(d1, d2));
        for (int i = 0; i < daysDifference(d1,d2); i++){
            d1.add(Calendar.DAY_OF_MONTH,1);
        }
        System.out.println("день месяца  = "+d1.get(Calendar.DAY_OF_MONTH));

        int monthDiff = timeBtwCal.get(Calendar.MONTH);
        int daysDiff = timeBtwCal.get(Calendar.DAY_OF_MONTH) -1;// +  dayCorrection(d2, d1, epochTime, timeBtwCal);
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

        Calendar nowTime = Calendar.getInstance();
        Calendar birthdayTime = Calendar.getInstance();


        nowTime.clear();
        birthdayTime.clear();

        nowTime.setTime(now);
        birthdayTime.setTime(birthday);

        birthdayTime.set(Calendar.YEAR, nowTime.get(Calendar.YEAR));
        birthdayTime.set(Calendar.MONTH, birthdayTime.get(Calendar.MONTH));
        birthdayTime.set(Calendar.DAY_OF_MONTH, birthdayTime.get(Calendar.DAY_OF_MONTH));
// проверить, меньше ли месяц дня рождения текущего месяца. Если да (месяц ДР уже прошёл), и,
// если месяц совпадает, проверить, меньше ли день ДР, чем текущий день месяца (ДР уже прошёл), то YEAR++ в birthdayTime
        if (nowTime.get(Calendar.MONTH) >= birthdayTime.get(Calendar.MONTH) && nowTime.get(Calendar.DAY_OF_MONTH) > birthdayTime.get(Calendar.DAY_OF_MONTH)) {
//    YEAR++ в birthdayTime
            birthdayTime.set(Calendar.YEAR, nowTime.get(Calendar.YEAR) + 1);
        }

//        Date timeToNextBD = new Date(birthdayTime.getTimeInMillis() - nowTime.getTimeInMillis());
        Date nextBirthdayTimeDate = new Date(birthdayTime.getTimeInMillis());
        System.out.println(nextBirthdayTimeDate.getTime()-now.getTime());
        System.out.println("До дня рождения " + timeBetweenFromMonthToMillis(now, nextBirthdayTimeDate));
    }


    public static void averageTime(Date[] events) {
//        long sumOfDiffs = 0;
        long averageDiff = 0;
//        for (int i = 0; i < events.length - 1; i++) {
//    sumOfDiffs += Math.abs(events[i].getTime() + events[i+1].getTime());
//        }
        averageDiff = (events[events.length - 1].getTime() - events[0].getTime()) / (events.length - 1);
        Date firstEvent = new Date(events[0].getTime());
        Date nextEventAverage = new Date(events[0].getTime() + averageDiff);

        System.out.println("Среднее время между событиями " + yearsBetween(firstEvent, nextEventAverage) + " лет, " +
                timeBetweenFromMonthToMillis(firstEvent, nextEventAverage));
    }


    public static int dayCorrection(Calendar a1, Calendar a2, Calendar epoch, Calendar timeBtw) {
// выяснить меньшие даты из пар а и b
//        int aMinYear = a1.get(Calendar.YEAR);
//        int aMaxYear = a2.get(Calendar.YEAR);
//        int bMinYear = epoch.get(Calendar.YEAR);
//        int bMaxYear = timeBtw.get(Calendar.YEAR);
        Calendar aMinCal = (Calendar) a1.clone();
        Calendar aMaxCal = (Calendar) a2.clone();
        Calendar bMinCal = (Calendar) epoch.clone();
        Calendar bMaxCal = (Calendar) timeBtw.clone();


        if (a2.before(a1)) {
//            aMinYear = a2.get(Calendar.YEAR);
//            aMaxYear = a1.get(Calendar.YEAR);
            aMinCal = (Calendar) a2.clone();
            aMaxCal = (Calendar) a1.clone();
        }
//aMaxCal.set(Calendar.MONTH, -1);
//        bMaxCal.set(Calendar.MONTH);
        if (timeBtw.before(epoch)) {
//            bMinYear = timeBtw.get(Calendar.YEAR);
//            bMaxYear = epoch.get(Calendar.YEAR);
            bMinCal = (Calendar) timeBtw.clone();
            bMaxCal = (Calendar) epoch.clone();
        }
        int aSumDaysOfAllMonth = 0;
        while ((aMinCal.before(aMaxCal))) {
            aSumDaysOfAllMonth += aMinCal.getActualMaximum(Calendar.DAY_OF_MONTH);
            aMinCal.set(Calendar.MONTH, aMinCal.get(Calendar.MONTH) + 1);
//            System.out.println("Месяц а =" + aMinCal.get(Calendar.MONTH));
        }
        aSumDaysOfAllMonth -= aMaxCal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int bSumDaysOfAllMonth = 0;
        while ((bMinCal.before(bMaxCal))) {
            bSumDaysOfAllMonth += bMinCal.getActualMaximum(Calendar.DAY_OF_MONTH);
            bMinCal.set(Calendar.MONTH, bMinCal.get(Calendar.MONTH) + 1);
//            System.out.println("Месяц b =" + bMinCal.get(Calendar.MONTH));
        }
        bSumDaysOfAllMonth -= bMaxCal.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println(aSumDaysOfAllMonth + "     " + bSumDaysOfAllMonth);

        return aSumDaysOfAllMonth - bSumDaysOfAllMonth;
    }

    public static int daysDifference(Calendar day1, Calendar day2) {
        Calendar d1 = (Calendar) day1.clone();
        Calendar d2 = (Calendar) day2.clone();

        boolean aSign = true;
// делаем d1 > d2
        if (d2.before(d1)) {

            d1 = (Calendar) day2.clone();
            d2 = (Calendar) day1.clone();
        }

        Calendar forDaysCount = (Calendar) d1.clone();
        int daysInTheStartMonth = d1.getActualMaximum(Calendar.DAY_OF_MONTH);
        int daysInTheEndMonth = d2.getActualMaximum(Calendar.DAY_OF_MONTH);

        int daysToMonthEnd = d1.getActualMaximum(Calendar.DAY_OF_MONTH) - d1.get(Calendar.DAY_OF_MONTH);
        int daysFromMonthstart = d2.get(Calendar.DAY_OF_MONTH);
        int sumDaysOfAllMonth = 0;
        while ((d1.before(d2))) {
            sumDaysOfAllMonth += d1.getActualMaximum(Calendar.DAY_OF_MONTH);
            d1.set(Calendar.MONTH, d1.get(Calendar.MONTH) + 1);
        }
        int res = daysToMonthEnd + daysFromMonthstart + sumDaysOfAllMonth - daysInTheStartMonth - daysInTheEndMonth;
        forDaysCount.add(Calendar.DAY_OF_MONTH, res);
//        System.out.println("daysDifference = " + res);
//        System.out.println("day of month = " + forDaysCount.get(Calendar.DAY_OF_MONTH));
//        return forDaysCount.get(Calendar.DAY_OF_MONTH);
        return res;
    }


    public static void main(String[] args) {
        Date d1 = new Date(2592000000L * 2);
        Date d2 = new Date(2592000000L * 4);
        timeBetween(d1, d2);

        Calendar birthDayExpectancy = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        birthDayExpectancy.clear();
        birthDayExpectancy.set(2022, 9, 27, 8, 44, 10);
        for (int i = 0; i < 40; i++){
            birthDayExpectancy.add(Calendar.DAY_OF_MONTH, 1);
        }
        System.out.println("день месяца через миллисекунды = "+birthDayExpectancy.get(Calendar.DAY_OF_MONTH));
        Date currentTime = new Date(birthDayExpectancy.getTimeInMillis());

        Calendar myBirthday = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        myBirthday.clear();
        myBirthday.set(1979, 11, 7, 0, 13, 53);
        Date myBirthdayDate = new Date(myBirthday.getTimeInMillis());

        Calendar test1 = Calendar.getInstance();
        Calendar test2 = Calendar.getInstance();
        test1.set(2049, 5, 17, 12, 1, 7);
        test2.set(2087, 9, 12, 18, 34, 17);
        Date testDate1 = new Date(test1.getTimeInMillis());
        Date testDate2 = new Date(test2.getTimeInMillis());
//        timeBetween(testDate1, testDate2);
        System.out.println();
//        timeBetween(testDate2, testDate1);

        timeToBirthday(currentTime, myBirthdayDate);
        /*
        ERROR: Тест "Метод timeBetween(Date date1, Date date2)" не пройден. Метод, вызванный с параметрами, соответствующими 17 июня 2049 года, 12:01:07.265 и
        12 октября 2087 года, 18:34:17.196 вывел на консоль:
Между date1 и date2 38 лет, 3 месяцев, 26 дней, 6 часов, 33 минут, 9 секунд, 931 миллисекунд

Ожидалось:
Между date1 и date2 38 лет, 3 месяцев, 25 дней, 6 часов, 33 минут, 9 секунд, 931 миллисекунд

ERROR: Тест "Метод timeToBirthday(Date birthday)" не пройден.
Метод, вызванный с параметром, соответствующим 07 декабря 1979 года, 00:13:53.976,
дата и время выполнения 27 октября 2022 года, 08:44:10.357 вывел на консоль:
До дня рождения 1 месяцев, 8 дней, 15 часов, 29 минут, 43 секунд, 619 миллисекунд

Ожидалось:
До дня рождения 1 месяцев, 9 дней, 15 часов, 29 минут, 43 секунд, 619 миллисекунд
         */
    }
}
