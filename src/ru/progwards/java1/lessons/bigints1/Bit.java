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
        Bit[] eightBits = new Bit[8];

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


    public static void main(String[] args) {
        System.out.println(new Bit(true));
        System.out.println(new ByteRegister((byte) 29));
        System.out.println(new ByteRegister((byte) 29).toDecString());
    }
}
