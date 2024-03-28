package ru.progwards.java2.lessons.patterns.AsbIntegerFactory;

public class ByteInteger extends AbsInteger implements ByteIntegerBuilder {
    byte byteNumber;

    public ByteInteger(byte byteNumber) {
        this.byteNumber = byteNumber;
    }

    @Override
    public String toString() {
        return "value = " + byteNumber;
    }

    @Override
    public long toLong() {
        return (long) byteNumber;
    }

    @Override
    public byte toByte() {
        return byteNumber;
    }
}
