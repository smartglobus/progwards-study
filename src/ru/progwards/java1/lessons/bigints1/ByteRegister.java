package ru.progwards.java1.lessons.bigints1;

public class ByteRegister {
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



    public static class ByteCounter {

        public static void inc(Bit.ByteRegister value) {
            for (int i = 0; i < 8; i++) {
                value.eightBits[i].value = !value.eightBits[i].value;
                if (value.eightBits[i].value) break;
            }
        }

        public static void dec(Bit.ByteRegister value) {
            for (int i = 0; i < 8; i++) {
                value.eightBits[i].value = !value.eightBits[i].value;
                if (!value.eightBits[i].value) break;
            }
        }

    }

    public static class ByteShiftRegister {
        public static void left(Bit.ByteRegister value) {
            for (int i = 7; i > 0; i--) {
                value.eightBits[i].value = value.eightBits[i - 1].value;
            }
            value.eightBits[0].value = false;
        }

        public static void right(Bit.ByteRegister value) {
            for (int i = 0; i < 7; i++) {
                value.eightBits[i].value = value.eightBits[i + 1].value;
            }
            value.eightBits[7].value = true;
        }
    }


}
