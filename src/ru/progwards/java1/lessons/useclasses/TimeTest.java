package ru.progwards.java1.lessons.useclasses;

public class TimeTest {
    public static void main(String[] args) {
        Time time000 = new Time(0,0,0);
        Time time0 = time000;
        System.out.println(time0.toString());
        Time time123 = new Time(1,2,3);
        Time time1 = time123;
        System.out.println(time1.toString());
        Time time111213 = new Time(11,12,13);
        Time time2 = time111213;
        System.out.println(time2.toString());
    }
}
