package ru.progwards.java1.lessons.useclasses;

public class TimeTest {
    public static void main(String[] args) {
        Time time000 = new Time(0, 0, 0);
        Time time0 = time000;
        System.out.println(time0.toString());
        Time time123 = new Time(1, 2, 3);
        Time time1 = time123;
        System.out.println(time1.toString());
        Time time111213 = new Time(11, 12, 13);
        Time time2 = time111213;
        System.out.println(time2.toString());

        System.out.println("\nв time0 " + time0.toSeconds() + " секунд");
        System.out.println("в time1 " + time1.toSeconds() + " секунд");
        System.out.println("в time2 " + time2.toSeconds() + " секунд");

        System.out.println("\nразница между time0 и time1 равна " + time0.secondsBetween(time1));
        System.out.println("разница между time2 и time0 равна " + time2.secondsBetween(time0));
        System.out.println("разница между time1 и time2 равна " + time1.secondsBetween(time2));
    }
}
