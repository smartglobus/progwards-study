package ru.progwards.java1.lessons.bigints1;

public class Bit {
    boolean value;

    public Bit() {
        value = false;
    }

    public Bit(boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value ? "1" : "0";
    }



    public static void main(String[] args) {
//        System.out.println(new Bit(true));
//        System.out.println(new ByteRegister((byte) 255));
        ByteRegister a = new ByteRegister((byte) 21);
        System.out.println(a.toDecString());
        System.out.println(a);
//        ByteCounter.dec(a);
        System.out.println(a.toDecString());
        System.out.println(a);
        ByteRegister b = new ByteRegister((byte) 234);
        System.out.println(b);
//        ByteShiftRegister.right(b);
        System.out.println(b.toDecString());
        System.out.println(b);

    }
}
