package ru.progwards.java1.lessons.useclasses;

public class CountTest {

    public static void testInc(int count) {
        Count countInc = new Count();
        for (int i = 0; i < count; i++) {
            countInc.inc();
            System.out.print(countInc.getCount() + " ");
        }
        System.out.println("\nтест inc окончен");
    }

    public static void testDec(int count) {
        Count countDec = new Count(count);
        while (true) {
            countDec.dec();
            System.out.print(countDec.getCount() + " ");
            count++;
            if (countDec.dec()) {

                System.out.println("count равен 0");
                break;
            }
        }
        System.out.println("\nтест dec окончен");
    }

    public static void main(String[] args) {
        testInc(7);
        testInc(0);
        testInc(-1);
        testDec(9);
        testDec(0);
        testDec(-5);
    }
}
