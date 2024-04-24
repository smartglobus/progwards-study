package ru.progwards.java2.lessons.sort;

public class QuickSort {

    public static int hoare(int[] a, int left, int right) {
        int pivot = a[(left + right) / 2];
        int i = left - 1;
        int j = right + 1;
        for (; ; ) {
            do {
                i++;
            } while (a[i] < pivot);

            do {
                j--;
            } while (a[j] > pivot);

            if (i >= j)
                return j;

            swap(a, i, j);
        }
    }


    static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static void sortHoare(int[] a, int left, int right) {
        if (left < right) {
            int pivot = hoare(a, left, right);
            sortHoare(a, left, pivot);
            sortHoare(a, pivot + 1, right);
        }
    }


    public static void quickSort(int[] a) {
        sortHoare(a, 0, a.length - 1);
    }

}
