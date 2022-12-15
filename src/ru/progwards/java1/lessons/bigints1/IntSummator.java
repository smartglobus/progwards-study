package ru.progwards.java1.lessons.bigints1;

import javax.sound.midi.Soundbank;

public class IntSummator {
    public static void add(IntRegister value1, IntRegister value2) {
        // пока исходим из того, что оба Register одного типа
        Summator.add(value1, value2);
// not ready!!!!!

//        boolean a;
//        boolean b;
//        boolean addOn = false;
//        boolean res;
//
//        for (int i = 0; i < value1.regVolume; i++) {
//            a = value1.register[i].value;
//            b = value2.register[i].value;
//            res = a ^ b ^ addOn;
//            addOn = a & b || a & addOn || b & addOn;
//            value1.register[i].value = res;
//        }
    }

    public static void sub(Register value1, Register value2) {
        value2 = toTwosComplement(value2);
        Summator.add(value1, value2);

    }

    private static Register toTwosComplement(Register value) {
        for (int i = 0; i < value.regVolume; i++) {
            value.register[i].value = !value.register[i].value;
        }
        Counter.inc(value);
        return value;
    }

    public static void main(String[] args) {
        IntRegister one = new IntRegister(25);
        IntRegister two = new IntRegister(-50);
        sub(one, two);
        System.out.println(one);
        System.out.println(one.toDecString());
        System.out.println(Integer.toBinaryString(17 + 256 * 256 * 256 * 128));
//        System.out.println(Integer.toBinaryString(9));
        System.out.println(Integer.toBinaryString(-9));

        System.out.println(Integer.toBinaryString(17 - 9));
    }
}
