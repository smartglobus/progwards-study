package ru.progwards.java1.lessons.params;

import java.util.Arrays;

public class ArrayInteger {
    byte[] digits;

    ArrayInteger(int n) {
        digits = new byte[n];
    }


    void fromString(String value) {

        char[] valueToCharArray = value.toCharArray();
        for (int i = digits.length - 1, j = 0; i >= 0 && j < valueToCharArray.length; i--, j++) {
            digits[i] = (byte) Character.digit(valueToCharArray[j], 10);
        }
    }

    @Override
    public String toString() {

        long forString = 0;
        for (int i = digits.length - 1; i >= 0; i--) {
            forString = forString * 10 + digits[i];
        }

        return Long.toString(forString);
    }

    boolean add(ArrayInteger num) {
        boolean overload = true;

        // создание двух массивов длиной 10, равной числу цифр в Integer.MAX_VALUE
        byte[] a = new byte[10];
        byte[] b = new byte[10];


        // заполнение массивов a и b
        for (int i = 0; i < 10; i++) {
            if (digits.length - 1 < i) {
                break;
            }
            a[i] = digits[i];
        }

        for (int i = 0; i < 10; i++) {
            if (num.digits.length - 1 < i) {
                break;
            }

            b[i] = num.digits[i];
        }

        for (int i = 0; i < 9; i++) {
            a[i] += b[i];

            if (a[i] > 9) {
                a[i] -= 10;
                a[i + 1]++;
            }
        }

        a[9] += b[9];
        if (a[9] > 9) {
            overload = false;
            Arrays.fill(digits, (byte) 0);

        }
        digits = a;

        if (Long.valueOf(toString()) > Integer.MAX_VALUE) {
            overload = false;
            Arrays.fill(digits, (byte) 0);
        }
        return overload;
    }

    public static void main(String[] args) {
        ArrayInteger test1 = new ArrayInteger(11);

        ArrayInteger test2 = new ArrayInteger(2);
        test1.fromString("123456789");
        System.out.println(test1);
        System.out.println(test1.add(test2));
        System.out.println(test1);
    }
}
