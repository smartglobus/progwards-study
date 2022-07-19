package ru.progwards.java1.lessons.wrappers;

public class NumMetrics {

    public static Integer sumDigits(Integer number) {

        int hundreds = number / 100;
        int dozens = number % 100 / 10;
        int digits = number % 10;
        return Integer.valueOf(hundreds + dozens + digits);
    }

    public static Integer mulDigits(Integer number) {

        int hundreds = number / 100;
        int dozens = number % 100 / 10;
        int digits = number % 10;
        return Integer.valueOf(hundreds * dozens * digits);
    }

    public static void main(String[] args) {
        System.out.println(sumDigits(123));
        System.out.println(mulDigits(423));
    }
}
