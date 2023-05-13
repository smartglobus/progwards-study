package ru.progwards.java1.lessons.generics;


import java.util.Arrays;

public class DynamicArray<T extends Object> {
    private T[] array;
    private int entryCount;

    //    @SuppressWarnings("unchecked")
     DynamicArray() {
        array = (T[]) new Object[1];
    }

    //    @SuppressWarnings("unchecked")
     void add(T t) {
        if (entryCount >= array.length) {
            T[] newArray = (T[]) new Object[array.length * 2];
            System.arraycopy(array, 0, newArray, 0, array.length);
            array = newArray;
        }
        array[entryCount++] = t;
    }

    //    @SuppressWarnings("unchecked")
     void insert(int pos, T t) {
        if (pos > array.length - 1) return;
        if (entryCount >= array.length) {
            T[] newArray = (T[]) new Object[array.length * 2];
            System.arraycopy(array, 0, newArray, 0, array.length);
            array = newArray;
        }
        if (entryCount - pos >= 0) System.arraycopy(array, pos, array, pos + 1, entryCount - pos);
        array[pos] = t;
        entryCount++;
    }

     void remove(int pos) {
        if (pos >= entryCount) return;
        System.arraycopy(array, pos + 1, array, pos, entryCount - 1 - pos);
        array[entryCount - 1] = null;
        entryCount--;
    }

    T get(int pos) {
        if (pos > array.length - 1) return null;
        return array[pos];
    }

     int size() {
        return entryCount;
    }


    public static void main(String[] args) {
        DynamicArray da = new DynamicArray<>();
        da.add(1);
        da.add(2);
        da.add(3);
        da.add(222);
        Arrays.stream(da.array).forEach(System.out::println);

        System.out.println("\nentryCount = " + da.entryCount + "\n");
//        da.insert(0, 5);
//        da.insert(2, 4);

        Arrays.stream(da.array).forEach(System.out::println);
        System.out.println("\nDynarray size = " + da.size() + "\n");
//        da.remove(2);
        da.remove(3);
        Arrays.stream(da.array).forEach(System.out::println);

    }
}
