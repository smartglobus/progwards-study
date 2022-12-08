package ru.progwards.java1.lessons.bigints1;

public class IntRegister {
    public Bit[] thirtyTwoBits = new Bit[32];

    public IntRegister() {
        for (int i = 0; i < 32; i++) {
            thirtyTwoBits[i] = new Bit(false);
        }
    }

    public IntRegister(int value) {
        this();
        for (int i = 0, j = 1; i < 32; i++, j <<= 1) {
            if ((value & j) != 0) {
                thirtyTwoBits[i].value = true;
            }
        }
    }

    @Override
    public String toString() {
        String result = "";
        boolean firstTrue = false;
        for (int i = 31; i >= 0; i--) {
            if (i == 0 && !firstTrue) result = "0";
            if (!thirtyTwoBits[i].value && !firstTrue) continue;
            if (thirtyTwoBits[i].value) firstTrue = true;
            result = result + thirtyTwoBits[i].toString();
        }
        return result;
    }

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

    public static void main(String[] args) {
        IntRegister a = new IntRegister(-120);
        System.out.println(Integer.toBinaryString(-120) + "   (check!)");
        System.out.println(a);
        System.out.println(a.toDecString());
    }
}
