package ru.progwards.java1.lessons.bigints1;

public class Bit {
    boolean value;

    public Bit() {
    }

    public Bit(boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value ? "1" : "0";
    }


    public static class ByteRegister extends Bit {
        public Bit[] eightBits = new Bit[8];

        public ByteRegister() {
            for (int i = 0; i < 8; i++) {
                eightBits[i] = new Bit(false);
            }
        }

        public ByteRegister(byte value) {
            this();
            for (int i = 0, j = 1; i < 8; i++, j <<= 1) {
                if ((value & j) != 0) {
                    eightBits[i].value = true;
                }
            }
        }

        @Override
        public String toString() {
            String result = "";
            for (Bit bit : eightBits) {
                result = bit.toString() + result;
            }
            return result;
        }

        public String toDecString() {
            int result = eightBits[7].value ? 1 : 0;
            for (int i = 6; i >= 0; i--) {
                result = result * 2 + (eightBits[i].value ? 1 : 0);
            }
            return Integer.toString(result);
        }

    }

    public static class ByteCounter {

        public static void inc(ByteRegister value) {
            for (int i = 0; i < 8; i++) {
                value.eightBits[i].value = !value.eightBits[i].value;
                if (value.eightBits[i].value) break;
            }
        }

        public static void dec(ByteRegister value) {
            for (int i = 0; i < 8; i++) {
                value.eightBits[i].value = !value.eightBits[i].value;
                if (!value.eightBits[i].value) break;
            }
        }

    }

    public static class ByteShiftRegister {
        public static void left(ByteRegister value) {
            for (int i = 7; i > 0; i--) {
                value.eightBits[i].value = value.eightBits[i - 1].value;
            }
            value.eightBits[0].value = false;
        }

        public static void right(ByteRegister value) {

        }
    }

    public static void main(String[] args) {
//        System.out.println(new Bit(true));
//        System.out.println(new ByteRegister((byte) 255));
        ByteRegister a = new ByteRegister((byte) 0);
        System.out.println(a.toDecString());
        System.out.println(a);
        ByteCounter.dec(a);
        System.out.println(a.toDecString());
        System.out.println(a);
        ByteRegister b = new ByteRegister((byte) 234);
        System.out.println(b);
        ByteShiftRegister.left(b);
        System.out.println(b.toDecString());
        System.out.println(b);

//        System.out.println(Integer.toBinaryString(232));
//        System.out.println(Integer.toBinaryString(231));
    }
}
