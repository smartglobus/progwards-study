package ru.progwards.java1.lessons.generics;

import java.util.Arrays;

public class ArraySort <T>{
    private T[] arrToSort;

    public static <T extends Comparable> void sort(T... arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i].compareTo(arr[j]) == 1) {
                    T temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        ArraySort<Integer> tst = new ArraySort<>();
        tst.arrToSort = new Integer[]{25, 6, 34, 87, 9, 43, 1};
        sort(tst.arrToSort);
        Arrays.stream(tst.arrToSort).forEach(System.out::println);
    }
}
