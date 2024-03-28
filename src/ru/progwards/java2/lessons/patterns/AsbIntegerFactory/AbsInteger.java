package ru.progwards.java2.lessons.patterns.AsbIntegerFactory;

public abstract class AbsInteger {

    public AbsInteger() {
    }

    public abstract String toString();

    static AbsInteger add(AbsInteger num1, AbsInteger num2) {
        long sum = num1.toLong() + num2.toLong();
        if (sum >= Byte.MIN_VALUE && sum <= Byte.MAX_VALUE) {
            return new ByteInteger((byte) sum);
        }
        if (sum >= Short.MIN_VALUE && sum <= Short.MAX_VALUE){
            return new ShortInteger((short) sum);
        }
        return new IntInteger((int) sum);
    }

    abstract long toLong();
}

