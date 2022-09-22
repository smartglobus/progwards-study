package ru.progwards.java1.lessons.arrays2;

import java.util.Arrays;

public class IntArrays {

    public static String toString(int[] a) {
        if (a.length == 0) return "[]";
        String toString = "[";
        for (int i = 0; i < a.length - 1; i++) {
            toString += a[i] + ", ";
        }
        return toString + a[a.length - 1] + "]";
    }

    public static boolean equals1(int[] a1, int[] a2) {
        if (a1.length != a2.length) {
            return false;
        }
        sort(a1);
        sort(a2);
        for (int i = 0; i < a1.length; i++) {
            if (a1[i] != a2[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean equals2(int[] a1, int[] a2) {
        Arrays.sort(a1);
        Arrays.sort(a2);
        return Arrays.equals(a1, a2);
    }

    public static void sort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[i] > a[j]) {
                    int temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] test0 = {};
        int[] test1 = {1};
        int[] test13579 = {1, 3, 5, 7, 9};
        System.out.println(toString(test0));
        System.out.println(Arrays.toString(test0));
        System.out.println(toString(test1));
        System.out.println(Arrays.toString(test1));
        System.out.println(toString(test13579));
        System.out.println(Arrays.toString(test13579));

        int[] a1 = {1, 2, 3};
        int[] a2 = {3, 2, 1};
        System.out.println(equals2(a1, a2));
        System.out.println(equals1(a1, a2));
        int[] b1 = {1, 2, 3};
        int[] b2 = {1, 5, 3};
        System.out.println(equals2(b1, b2));
        System.out.println(equals1(b1, b2));
    }
}
