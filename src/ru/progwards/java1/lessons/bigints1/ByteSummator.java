package ru.progwards.java1.lessons.bigints1;

public class ByteSummator {
    public static boolean add(Bit.ByteRegister value1, Bit.ByteRegister value2) {
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
