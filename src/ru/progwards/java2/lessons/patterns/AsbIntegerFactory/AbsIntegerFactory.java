package ru.progwards.java2.lessons.patterns.AsbIntegerFactory;

public enum AbsIntegerFactory implements AbstractNumberFactory {
    INSTANCE;

    private AbsIntegerFactory() {
    }

    @Override
    public AbsNumber getAbsInteger(int number) {
        if (number >= Byte.MIN_VALUE && number <= Byte.MAX_VALUE) {
            return new ByteInteger((byte) number);
        }
        if (number >= Short.MIN_VALUE && number <= Short.MAX_VALUE) {
            return new ShortInteger((short) number);
        }
        return new IntInteger(number);
    }
}
