package ru.progwards.java1.lessons.bigints1;

public class ByteRegister extends Register{
    public Bit[] eightBits;

    public ByteRegister() {
        super(8);
        eightBits = super.register;
    }

    public ByteRegister(byte value) {
        super(8,value);
        eightBits = super.register;
    }

    @Override
    public String toDecString() {
        int result = eightBits[7].value ? 1 : 0;
        for (int i = 6; i >= 0; i--) {
            result = result * 2 + (eightBits[i].value ? 1 : 0);
        }
        return Integer.toString(result);
    }



    public static class ByteCounter extends Counter{

        public static void inc(Register value) {
//            for (int i = 0; i < 8; i++) {
//                value.eightBits[i].value = !value.eightBits[i].value;
//                if (value.eightBits[i].value) break;
//            }
            Counter.inc(value);
        }

        public static void dec(Register value) {
//            for (int i = 0; i < 8; i++) {
//                value.eightBits[i].value = !value.eightBits[i].value;
//                if (!value.eightBits[i].value) break;
//            }
            Counter.dec(value);
        }

    }

    public static class ByteShiftRegister extends ShiftRegister {
        public static void left(Register value) {
//            for (int i = 7; i > 0; i--) {
//                value.eightBits[i].value = value.eightBits[i - 1].value;
//            }
//            value.eightBits[0].value = false;
            ShiftRegister.left(value);
        }

        public static void right(Register value) {
//            for (int i = 0; i < 7; i++) {
//                value.eightBits[i].value = value.eightBits[i + 1].value;
//            }
//            value.eightBits[7].value = true;
            ShiftRegister.right(value);
        }
    }

    public static class ByteSummator extends Summator {

        public static boolean add(ByteRegister value1, ByteRegister value2) {
            boolean isSumTrue = Integer.valueOf(value1.toDecString()) + Integer.valueOf(value2.toDecString()) <= 255;
            boolean a;
            boolean b;
            boolean addOn = false;
            boolean res;

            for (int i = 0; i < 8; i++) {
                a = value1.eightBits[i].value;
                b = value2.eightBits[i].value;
                res = a ^ b ^ addOn;
                addOn = a & b || a & addOn || b & addOn;
                value1.eightBits[i].value = res;
            }

            return isSumTrue;
        }
    }

    public static void main(String[] args) {
//        System.out.println(new Bit(true));
//        System.out.println(new ByteRegister((byte) 255));
        ByteRegister a = new ByteRegister((byte) 10);
        System.out.println(a.toDecString());
        System.out.println(a);
//        ByteCounter.inc(a);
        System.out.println(a.toDecString());
        System.out.println(a);
        System.out.println("----------");
        ByteRegister b = new ByteRegister((byte) 233);
        System.out.println(b);
//        ByteShiftRegister.left(b);
        System.out.println(b.toDecString());
        System.out.println(b);
        System.out.println(ByteSummator.add(a, b));


//        System.out.println(Integer.toBinaryString(232));
//        System.out.println(Integer.toBinaryString(231));
    }
}
