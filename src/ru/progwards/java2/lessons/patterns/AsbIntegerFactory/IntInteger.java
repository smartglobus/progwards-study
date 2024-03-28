package ru.progwards.java2.lessons.patterns.AsbIntegerFactory;

public class IntInteger extends AbsInteger implements IntIntegerBuilder {
    int intNumber;

    public IntInteger(int intNumber) {
        this.intNumber = intNumber;
    }

    @Override
    public String toString() {
        return "value = " + intNumber;
    }

    @Override
    public long toLong() {
        return (long) intNumber;
    }

    @Override
    public int toInt() {
        return intNumber;
    }
}
