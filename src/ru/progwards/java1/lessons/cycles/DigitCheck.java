package ru.progwards.java1.lessons.cycles;

public class DigitCheck {

    public static boolean containsDigit(int number, int digit) {
        do {
            if (number % 10 == digit) return true;
            number /= 10;
        }
        while (number != 0);
        return false;
    }

    public static void main(String[] args) {
        System.out.println(containsDigit(1232454, 8));
        System.out.println(containsDigit(1232454, 0));
        System.out.println(containsDigit(0, 0));
        System.out.println(containsDigit(1232454, 1));
    }
}
