package ru.progwards.java1.lessons.generics;


public class DynamicArray<T> {
    private T[] array;
    private int entryCount;

    DynamicArray() {
        array = (T[]) new Object[1];
    }

    void add(T t) {
        if (entryCount >= array.length) {
            T[] newArray = (T[]) new Object[array.length * 2];
            System.arraycopy(array, 0, newArray, 0, array.length);
            array = newArray;
        }
        array[entryCount++] = t;
        System.out.println(t.getClass());
    }

    void insert(int pos, T t) {
        if (pos > array.length - 1) return;
        if (entryCount >= array.length) {
            T[] newArray = (T[]) new Object[array.length * 2];
            System.arraycopy(array, 0, newArray, 0, array.length);
            array = newArray;
        }
        for (int i = entryCount - 1; i >= pos; i--) array[i + 1] = array[i];
        array[pos] = t;
        entryCount++;
    }

    void remove(int pos) {
        if (pos > array.length - 1) return;
        for (int i = pos; i < entryCount - 1; i++) array[i] = array[i + 1];
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

//        for (Integer i : (Integer[]) da.array) System.out.println((Integer) i);
//        System.out.println(da.array[1].getClass().equals(Object.class));
        System.out.println(5 + da.array[0].toString() + "arr");
        System.out.println(da.entryCount);
        da.insert(0, 5);

//        Arrays.stream(da.array).forEach(System.out::println);
        System.out.println("\n" + "Dynarray size = " + da.size() + "\n");
        da.remove(0);
//        Arrays.stream(da.array).forEach(System.out::println);

    }
}
