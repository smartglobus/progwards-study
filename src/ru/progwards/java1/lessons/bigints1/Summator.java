package ru.progwards.java1.lessons.bigints1;

public class Summator {
    public static void add(Register value1, Register value2){

        boolean a;
        boolean b;
        boolean addOn = false;
        boolean res;

        for (int i = 0; i < value1.regVolume; i++) {
            a = value1.register[i].value;
            b = value2.register[i].value;
            res = a ^ b ^ addOn;
            addOn = a & b || a & addOn || b & addOn;
            value1.register[i].value = res;
        }
    }

    public static void sub(Register value1, Register value2) {
       toTwosComplement(value2);
        Summator.add(value1, value2);

    }

    private static Register toTwosComplement(Register value) {

        for (int i = 0; i < value.regVolume; i++) {
            value.register[i].value = !value.register[i].value;
        }
        Counter.inc(value);
        return value;
    }
}
