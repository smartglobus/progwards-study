package ru.progwards.java1.lessons.generics;

public class DynamicArray<T> {
    private T[] array;
    private int entryCount;

    @SuppressWarnings("unchecked")
    DynamicArray() {
        array = (T[]) new Object[1];
    }

    @SuppressWarnings("unchecked")
    void add(T t) {
        if (entryCount >= array.length) arrayExpand();
        array[entryCount++] = t;
    }

    @SuppressWarnings("unchecked")
    void insert(int pos, T t) {
        if (pos > entryCount) throw new RuntimeException(">");
        if (pos < 0) throw new RuntimeException("<");

        if (entryCount >= array.length) arrayExpand();
        if (entryCount - pos >= 0) System.arraycopy(array, pos, array, pos + 1, entryCount - pos);
        array[pos] = t;
        entryCount++;
    }

    void remove(int pos) {
        checkPos(pos);
        System.arraycopy(array, pos + 1, array, pos, entryCount - 1 - pos);
        array[entryCount - 1] = null;
        entryCount--;
    }

    T get(int pos) {
        checkPos(pos);
        return array[pos];
    }

    int size() {
        return entryCount;
    }

    private void checkPos(int pos) {
        if (pos >= entryCount) throw new RuntimeException(">=");
        if (pos < 0) throw new RuntimeException("<");
    }

    @SuppressWarnings("unchecked")
    void arrayExpand() {
        T[] newArray = (T[]) new Object[array.length * 2];
        System.arraycopy(array, 0, newArray, 0, array.length);
        array = newArray;
    }


    public static void main(String[] args) {
        DynamicArray<Integer> da = new DynamicArray<>();
        da.add(1);
        da.add(2);
        da.add(3);
        da.add(222);
        for (int i = 0; i < da.size(); i++) System.out.println(da.get(i));

        System.out.println("\nentryCount = " + da.entryCount + "\n");
        da.insert(0, 5);
        da.insert(2, 4);

        for (int i = 0; i < da.size(); i++) System.out.println(da.get(i));
        System.out.println("\nDynarray size = " + da.size() + "\n");
        da.get(25);
        da.remove(3);
        for (int i = 0; i < da.size(); i++) System.out.println(da.get(i));
    }
}
