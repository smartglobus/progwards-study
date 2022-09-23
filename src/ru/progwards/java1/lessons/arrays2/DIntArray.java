package ru.progwards.java1.lessons.arrays2;

import java.util.Arrays;

public class DIntArray {
    private int[] dIntArray;

    DIntArray() {

    }

    public void add(int num) {
        int[] newArrayAdd = new int[dIntArray.length+1];
        newArrayAdd = Arrays.copyOf(dIntArray, newArrayAdd.length);
        newArrayAdd[newArrayAdd.length - 1] = num;
        dIntArray = Arrays.copyOf(newArrayAdd, newArrayAdd.length);
    }

    public void atInsert(int pos, int num) {
        int[] newArray;
        newArray = Arrays.copyOf(dIntArray, dIntArray.length + 1);
        for (int i = newArray.length - 1; i > pos; i--) {
            newArray[i] = newArray[i - 1];
        }
        newArray[pos] = num;
        dIntArray = Arrays.copyOf(newArray, newArray.length);
    }

    public void atDelete(int pos) {

        for (int i = pos; i < dIntArray.length - 1; i++) {
            dIntArray[i] = dIntArray[i + 1];
        }
        dIntArray = Arrays.copyOf(dIntArray, dIntArray.length - 1);
    }

    public int at(int pos) {
        return dIntArray[pos];
    }

    public static void main(String[] args) {
        DIntArray testArray = new DIntArray();
        int[] a = {1,3,5,7,9};
        testArray.setdIntArray(a);
        testArray.add(5);
        System.out.println(Arrays.toString(testArray.getdIntArray()));
        testArray.atInsert(3, 100);
        System.out.println(Arrays.toString(testArray.getdIntArray()));
        testArray.atDelete(2);
        System.out.println(Arrays.toString(testArray.getdIntArray()));
    }

    public void setdIntArray(int[] dIntArray) {
        this.dIntArray = dIntArray;
    }

    public int[] getdIntArray() {
        return dIntArray;
    }
}