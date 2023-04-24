package ru.progwards.java1.lessons.bigints1;

public class ByteRegister extends Register {
    public Bit[] eightBits;
//
    public ByteRegister() {
        super(8);
        eightBits = super.register;
    }
//
//    public ByteRegister(byte value) {
//        super(8, value);
//        eightBits = super.register;
//    }
//
    @Override
    public String toDecString() {
        int result = eightBits[7].value ? 1 : 0;
        for (int i = 6; i >= 0; i--) {
            result = result * 2 + (eightBits[i].value ? 1 : 0);
        }
        return Integer.toString(result);
    }
//
//
//    public static void main(String[] args) {
//
//        ByteRegister a = new ByteRegister((byte) 0);
//        System.out.println(a.toDecString());
//        System.out.println(a);
////        ByteCounter.inc(a);
//        ByteShiftRegister.right(a);
//        System.out.println(a.toDecString());
//        System.out.println(a);
//        System.out.println(Integer.toBinaryString((byte) 0b110000000 >> 1));
//        System.out.println("----------");
//        ByteRegister b = new ByteRegister((byte) 233);
//        System.out.println(b);
//
//        System.out.println(b.toDecString());
//        System.out.println(b);
//        System.out.println(ByteSummator.add(a, b));
//        ByteRegister zero = new ByteRegister();
//        System.out.println(zero);
//
//    }
}
