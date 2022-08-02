package ru.progwards.java1.lessons.cycles;

public class NumbersRange {

    public static long sumNumbers(int start, int finish) {
        long sum = 0;
        for (int i = start; i <= finish; i++) {
            sum += i;
        }
        return sum;
    }

    public static long sumOdd(int start, int finish) {
        if (Math.abs(start % 2) == 1) ++start;  // как вариант, if (start%2 == 1 || start%2 == -1)
        long sumOdd = 0;
        for (int i = start; i <= finish; i += 2) {
            sumOdd += i;
        }
        return sumOdd;
    }

    public static long sumEvenIdx(int start, int finish) {
        long sumEI = 0;
        for (int i = start; i <= finish; i += 2) {
            sumEI += i;
        }
        return sumEI;
    }

    public static void main(String[] args) {
        System.out.println(sumNumbers(-4, 4));
        System.out.println(sumNumbers(3, 7));
        System.out.println(sumNumbers(0, 5));
        System.out.println(sumNumbers(200000000, 2000001000));

        System.out.println("\n" + sumOdd(-10, 10));
        System.out.println(sumOdd(0, 17));

        System.out.println("\n" + sumEvenIdx(0, 10));
        System.out.println(sumEvenIdx(1,5));
    }
}
