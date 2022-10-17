package ru.progwards.java1.lessons.params;

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

class ByteInteger extends AbsInteger {
    byte byteNumber;

    ByteInteger(byte byteNumber) {
        this.byteNumber = byteNumber;
    }

    @Override
    public String toString() {
        return Byte.toString(byteNumber);
    }

    @Override
    long toLong() {
        return (long) byteNumber;
    }
}

class ShortInteger extends AbsInteger {
    short shortNumber;

    public ShortInteger(short shortNumber) {
        this.shortNumber = shortNumber;
    }

    @Override
    public String toString() {
        return Short.toString(shortNumber);
    }

    @Override
    long toLong() {
        return (long) shortNumber;
    }
}

class IntInteger extends AbsInteger {
    int intNumber;

    public IntInteger(int intNumber) {
        this.intNumber = intNumber;
    }

    @Override
    public String toString() {
        return Integer.toString(intNumber);
    }

    @Override
    long toLong() {
        return (long) intNumber;
    }

    public static void main(String[] args) {
        AbsInteger int1 = new IntInteger(214748364);
        AbsInteger short1 = new ShortInteger((short) 4567);
        AbsInteger byte1 = new ByteInteger((byte)125);
        System.out.println(add(int1,byte1));
        System.out.println(add(short1,byte1));
        System.out.println(Integer.MAX_VALUE);
    }
}