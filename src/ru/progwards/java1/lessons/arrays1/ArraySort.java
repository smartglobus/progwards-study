package ru.progwards.java1.lessons.arrays1;

public class ArraySort {
    public static void sort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[i] > a[j]) {
                    // Способ поменять местами значения в ячейках памяти без создания третьей переменной
                    a[i] = a[j] - a[i];
                    a[j] -= a[i];
                    a[i] += a[j];
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] a = {5, 2, 8, 36, -7, 0, 21, 366, -57};
        sort(a);
        for (int aSorted : a
        ) {
            System.out.println(aSorted);
        }
    }
}
