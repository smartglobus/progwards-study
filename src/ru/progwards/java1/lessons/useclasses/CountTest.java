package ru.progwards.java1.lessons.useclasses;

public class CountTest {

    public static void testInc(int count) {
        Count countInc = new Count();
        for (int i = 0; i < count; i++) {
            countInc.inc();
            System.out.print(countInc.getCount() + " ");
        }
        if (count > 0) System.out.println();
        System.out.println("тест inc окончен");
    }

    public static void testDec(int count) {
        Count countDec = new Count(count);
        boolean checkDec;

        do {
            checkDec = countDec.dec();
            System.out.print(countDec.getCount() + " ");

            if (countDec.getCount() == 0) {
                System.out.println("\ncount равен 0");
            }
        } while (!checkDec);
        if (count <= 0) System.out.println();
        System.out.println("тест dec окончен");
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
