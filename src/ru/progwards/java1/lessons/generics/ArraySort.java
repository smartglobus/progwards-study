package ru.progwards.java1.lessons.generics;

import java.util.Arrays;

public class ArraySort {

    public static <T extends Object & Comparable<? super T>> void sort(T... arr) {
        for (int i = 0; i < arr.length - 1; i++)
            for (int j = i + 1; j < arr.length; j++)
                if (arr[i].compareTo(arr[j]) >= 1) {
                    T temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
    }

    public static void main(String[] args) {
        Integer[] testArr = new Integer[]{25, 6, 34, 87, 9, 43, 1};
        Comparable[] fakearr= new Comparable[]{3,"abc",25.5f};
//        sort(fakearr);
        sort(testArr);
        Arrays.stream(testArr).forEach(System.out::println);
    }
}
