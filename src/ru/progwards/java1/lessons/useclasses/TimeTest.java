package ru.progwards.java1.lessons.useclasses;

public class TimeTest {
    public static void main(String[] args) {
        Time time = new Time(0,0,0);
        Time time0 = time;
        System.out.println(time0.toString());
    }
}
