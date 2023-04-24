package ru.progwards.java1.lessons.bigints1;

public abstract class Register {
public int regVolume;
public Bit[] register;

    public Register(int regVolume) {
        this.regVolume = regVolume;
        this.register = new Bit[regVolume];
        for (int i = 0; i < regVolume; i++) {
            register[i] = new Bit(false);
        }
    }

    public Register(int regVolume, int value){
        this(regVolume);
        for (int i = 0, j = 1; i < regVolume; i++, j <<= 1) {
            if ((value & j) != 0) {
                register[i].value = true;
            }
        }
    }

    public String toString() {
        String result = "";
        // закомментированы строки кода нужные для удаления лишних нулей вначале строки.
//        boolean firstTrue = false;
        for (int i = regVolume-1; i >= 0; i--) {
//            if (i == 0 && !firstTrue && !register[i].value) result = "0";
//            if (!register[i].value && !firstTrue) continue;
//            if (register[i].value) firstTrue = true;
            result = result + register[i].toString();
        }
        return result;
    }

    public abstract String toDecString();





    public static class ByteRegister extends Register {
        public Bit[] eightBits;

        public ByteRegister() {
            super(8);
            eightBits = super.register;
        }

        public ByteRegister(byte value) {
            super(8, value);
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
    }

    public static class IntRegister extends Register {
        public Bit[] thirtyTwoBits;

        public IntRegister() {
            super(32);
            thirtyTwoBits = super.register;
        }

        public IntRegister(int value) {
            super(32, value);
            thirtyTwoBits = super.register;
        }

        @Override
        public String toDecString() {
            int result = thirtyTwoBits[30].value ? 1 : 0;

            for (int i = 29; i >= 0; i--) {
                result = result * 2 + (thirtyTwoBits[i].value ? 1 : 0);
            }

            if (thirtyTwoBits[31].value) {
                result = Integer.MIN_VALUE + result;
            }

            return Integer.toString(result);
        }
    }

        public static void main(String[] args) {

    }
}
