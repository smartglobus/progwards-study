package ru.progwards.java1.lessons.date;

import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateDiff {
    public static void timeBetween(Date date1, Date date2) {

//        Calendar timeBtwCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
//        timeBtwCal.clear();
//
//        timeBtwCal.setTimeInMillis(Math.abs(date1.getTime() - date2.getTime()));
//
//        int yearDiff = timeBtwCal.get(Calendar.YEAR) - 1970;

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
//        Calendar zeroTime = Calendar.getInstance();
        Calendar nowTime = Calendar.getInstance();
        Calendar birthdayTime = Calendar.getInstance();

//        zeroTime.clear();
        nowTime.clear();
        birthdayTime.clear();

        nowTime.setTime(now);
        birthdayTime.setTime(birthday);

        birthdayTime.set(Calendar.YEAR, nowTime.get(Calendar.YEAR));
        birthdayTime.set(Calendar.MONTH, birthdayTime.get(Calendar.MONTH));
        birthdayTime.set(Calendar.DAY_OF_MONTH, birthdayTime.get(Calendar.DAY_OF_MONTH)-1);
// проверить, меньше ли месяц дня рождения текущего месяца. Если да (месяц ДР уже прошёл), и,
// если месяц совпадает, проверить, меньше ли день ДР, чем текущий день месяца (ДР уже прошёл), то YEAR++ в birthdayTime
        if (nowTime.get(Calendar.MONTH) >= birthdayTime.get(Calendar.MONTH) && nowTime.get(Calendar.DAY_OF_MONTH) > birthdayTime.get(Calendar.DAY_OF_MONTH)) {
//    YEAR++ в birthdayTime
            birthdayTime.set(Calendar.YEAR, nowTime.get(Calendar.YEAR) + 1);
        }

//        Date timeToNextBD = new Date(birthdayTime.getTimeInMillis() - nowTime.getTimeInMillis());
        Date nextBirthdayTimeDate = new Date(birthdayTime.getTimeInMillis());

//        Calendar timeToNextBdCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
//        timeToNextBdCal.setTimeInMillis(timeToNextBD.getTime());
//
//        int monthDiff = Math.abs(timeToNextBdCal.get(Calendar.MONTH) - zeroTime.get(Calendar.MONTH));
//        int daysDiff = Math.abs(timeToNextBdCal.get(Calendar.DAY_OF_MONTH) - zeroTime.get(Calendar.DAY_OF_MONTH));
//        int hoursDiff = Math.abs(timeToNextBdCal.get(Calendar.HOUR) - zeroTime.get(Calendar.HOUR));
//        int minutesDiff = Math.abs(timeToNextBdCal.get(Calendar.MINUTE) - zeroTime.get(Calendar.MINUTE)) + hoursDiff * 60;
//        int secondsDiff = Math.abs(timeToNextBdCal.get(Calendar.SECOND) - zeroTime.get(Calendar.SECOND));
//        int millisDiff = Math.abs(timeToNextBdCal.get(Calendar.MILLISECOND) - zeroTime.get(Calendar.MILLISECOND));

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


    public static void main(String[] args) {
        Date d1 = new Date(0);
        Date d2 = new Date(97200000L);

        timeBetween(d1, d2);
        Date currentTime = new Date(7200600);
        Calendar myBirthday = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        myBirthday.clear();
        myBirthday.set(1973, 11, 2);
        Date myBirthdayDate = new Date(myBirthday.getTimeInMillis());
        timeToBirthday(currentTime, myBirthdayDate);
    }
}
