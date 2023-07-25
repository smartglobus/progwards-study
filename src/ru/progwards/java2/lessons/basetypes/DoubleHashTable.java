package ru.progwards.java2.lessons.basetypes;

import java.util.ArrayList;

public class DoubleHashTable<T> implements HashValue {
    private TableEntry<T>[] table;


    DoubleHashTable() {
        table = new TableEntry[101];
    }

    @Override
    public int getHash(int key) {
        return 0;
    }

    private class TableEntry<T> {
        private T item;
        private int key;
        private TableEntry<T> next;//
        private boolean deleted;

    }

    private static int nextSize(int currSize) {
        int next = currSize * 2 + 1;
        boolean isPrime = false;
        while (!isPrime){
            for (int i = 3; i <= next / 2; i += 2) {
                if (next % i == 0) break;
                if (i == next / 2)
                    isPrime = true;
            }
            next+=2;
    }
        return next;
    }

    public static void main(String[] args) {
        DoubleHashTable<Integer> testDel = new DoubleHashTable<>();
        System.out.println(nextSize(101));
    }
}
