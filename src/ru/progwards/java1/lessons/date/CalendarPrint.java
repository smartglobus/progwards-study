package ru.progwards.java1.lessons.date;

import java.util.Calendar;
import java.util.Locale;

public class CalendarPrint {
    public static void printMonth(int month, int year) {
        Calendar prntMnth = Calendar.getInstance();
        Locale locale = new Locale("ru", "RU");
        prntMnth.set(Calendar.MONTH, month);
        prntMnth.set(Calendar.YEAR, year);
        System.out.println(prntMnth.get(Calendar.YEAR));
        System.out.println(prntMnth.getDisplayName(Calendar.MONTH, Calendar.LONG,locale));
    }

    public static void main(String[] args) {
        printMonth(1,2005);
    }
}
