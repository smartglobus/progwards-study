package ru.progwards.java2.lessons.patterns.AsbIntegerFactory;

public class ShortInteger extends AbsInteger implements ShortIntegerBuilder {
    short shortNumber;

    public ShortInteger(short shortNumber) {
        this.shortNumber = shortNumber;
    }

    @Override
    public String toString() {
        return "value = " + shortNumber;
    }

    @Override
    public long toLong() {
        return (long) shortNumber;
    }


    @Override
    public short toShort() {
        return shortNumber;
    }
}
