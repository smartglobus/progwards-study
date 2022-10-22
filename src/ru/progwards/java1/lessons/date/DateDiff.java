package ru.progwards.java1.lessons.date;

import java.util.Calendar;
import java.util.Date;

public class DateDiff {
    public static void timeBetween(Date date1, Date date2) {

        Calendar zeroTime = Calendar.getInstance();
        Calendar timeBtwCal = Calendar.getInstance();

        zeroTime.clear();
        timeBtwCal.clear();

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

    public static void timeToBirthday(Date now, Date birthday) {
        Calendar zeroTime = Calendar.getInstance();
        Calendar nowTime = Calendar.getInstance();
        Calendar birthdayTime = Calendar.getInstance();

        zeroTime.clear();
        nowTime.clear();
        birthdayTime.clear();

        nowTime.setTime(now);
        birthdayTime.setTime(birthday);
//        birthdayTime.setTime(birthday);
        birthdayTime.set(Calendar.YEAR, nowTime.get(Calendar.YEAR));
        birthdayTime.set(Calendar.MONTH, birthdayTime.get(Calendar.MONTH));
        birthdayTime.set(Calendar.DAY_OF_MONTH, birthdayTime.get(Calendar.DAY_OF_MONTH));
// проверить, меньше ли месяц дня рождения текущего месяца. Если да (месяц ДР уже прошёл), и,
// если месяц совпадает, проверить, меньше ли день ДР, чем текущий день месяца (ДР уже прошёл), то YEAR++ в birthdayTime
        if (nowTime.get(Calendar.MONTH) >= birthdayTime.get(Calendar.MONTH) && nowTime.get(Calendar.DAY_OF_MONTH) > birthdayTime.get(Calendar.DAY_OF_MONTH)) {
//    YEAR++ в birthdayTime
            birthdayTime.set(Calendar.YEAR, nowTime.get(Calendar.YEAR) + 1);
        }

        Date timeToNextBD = new Date(birthdayTime.getTimeInMillis() - nowTime.getTimeInMillis());
        Calendar timeToNextBdCal = Calendar.getInstance();
        timeToNextBdCal.setTimeInMillis(timeToNextBD.getTime());

        int monthDiff = Math.abs(timeToNextBdCal.get(Calendar.MONTH) - zeroTime.get(Calendar.MONTH));
        int daysDiff = Math.abs(timeToNextBdCal.get(Calendar.DAY_OF_MONTH) - zeroTime.get(Calendar.DAY_OF_MONTH));
        int hoursDiff = Math.abs(timeToNextBdCal.get(Calendar.HOUR) - zeroTime.get(Calendar.HOUR));
        int minutesDiff = Math.abs(timeToNextBdCal.get(Calendar.MINUTE) - zeroTime.get(Calendar.MINUTE)) + hoursDiff * 60;
        int secondsDiff = Math.abs(timeToNextBdCal.get(Calendar.SECOND) - zeroTime.get(Calendar.SECOND));
        int millisDiff = Math.abs(timeToNextBdCal.get(Calendar.MILLISECOND) - zeroTime.get(Calendar.MILLISECOND));

        System.out.println("До дня рождения " + monthDiff + " месяцев, " + daysDiff
                + " дней, " + minutesDiff + " минут, " + secondsDiff + " секунд, " + millisDiff + " миллисекунд");
    }

    public static void main(String[] args) {
        Date d1 = new Date(10800000);
        Date d2 = new Date(31554000000L);
        timeBetween(d1, d2);
        Date currentTime = new Date(System.currentTimeMillis());
        Calendar myBirthday = Calendar.getInstance();
        myBirthday.clear();
        myBirthday.set(1973, 8, 14);
        Date myBirthdayDate = new Date(myBirthday.getTimeInMillis());
        timeToBirthday(currentTime, myBirthdayDate);
    }
}
