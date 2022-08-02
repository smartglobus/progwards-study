package ru.progwards.java1.lessons.cycles;

public class NumbersRange {

    public static long sumNumbers(int start, int finish) {
        int sum = 0;
        for (int i = start; i <= finish; i++) {
            sum += i;
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(sumNumbers(-4, 4));
        System.out.println(sumNumbers(3, 7));
        System.out.println(sumNumbers(0, 5));
    }
}
