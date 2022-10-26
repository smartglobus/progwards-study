package ru.progwards.java1.lessons.date;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class CalendarPrint {
    public static void printMonth(int month, int year) {
        Calendar prntMnth = Calendar.getInstance();
        prntMnth.clear();


        prntMnth.set(year, month, 1, 0, 0, 0);
//        System.out.println(prntMnth);
//        System.out.println(prntMnth.get(Calendar.DAY_OF_WEEK));
        String[] monthNames = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
        System.out.print(prntMnth.get(Calendar.YEAR) + " ");
        System.out.println(monthNames[prntMnth.get(Calendar.MONTH)]);
        System.out.println("ПН ВТ СР ЧТ ПТ СБ ВС");

        int[] calendarTableInt = new int[42];
        int startPoint = 0;
//если 1-е число попадает на воскресенье
        if (prntMnth.get(Calendar.DAY_OF_MONTH) == 1 && prntMnth.get(Calendar.DAY_OF_WEEK) == 1) {
            startPoint = 6;
        } else {
            startPoint = prntMnth.get(Calendar.DAY_OF_WEEK) - 2;
        }

        for (int i = startPoint, j = 1; i < calendarTableInt.length; i++, j++) {
            if (i > 27 && prntMnth.get(Calendar.DAY_OF_MONTH) == 1) {
                break;
            }
//            System.out.println("prntMnth.get(Calendar.DAY_OF_MONTH)" + prntMnth.get(Calendar.DAY_OF_MONTH));
//            System.out.println("prntMnth.get(Calendar.DAY_OF_WEEK)" + prntMnth.get(Calendar.DAY_OF_WEEK));
            calendarTableInt[i] = prntMnth.get(Calendar.DAY_OF_MONTH);
            prntMnth.set(Calendar.DAY_OF_MONTH, j + 1);
        }

        for (int i = 0;  i < calendarTableInt.length; i++){
            if (calendarTableInt[i]==0){
                System.out.print("   ");
                continue;
            }
            if (calendarTableInt[i]<10){
                System.out.print(" "+calendarTableInt[i]+" ");
            }
            if (calendarTableInt[i]>=10){
                System.out.print(calendarTableInt[i]+" ");
            }
            if ((i+1)%7==0){
                System.out.println();
            }
        }






    }

    public static void main(String[] args) {
        printMonth(11, 2022);
    }
}
