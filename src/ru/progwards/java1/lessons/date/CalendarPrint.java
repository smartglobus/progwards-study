package ru.progwards.java1.lessons.date;

import java.util.Calendar;
import java.util.Locale;

public class CalendarPrint {
    public static void printMonth(int month, int year) {
        Calendar prntMnth = Calendar.getInstance();
        Locale locale = new Locale("ru", "RU");
        prntMnth.set(Calendar.MONTH, month);
        prntMnth.set(Calendar.YEAR, year);
        String[] monthNames = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
        System.out.print(prntMnth.get(Calendar.YEAR) + " ");
        System.out.println(monthNames[prntMnth.get(Calendar.MONTH)]);
    }

    public static void main(String[] args) {
        printMonth(1,2005);
    }
}
