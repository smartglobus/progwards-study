package ru.progwards.java1.lessons.bigints1;

public class Summator {
    public static void add(Register value1, Register value2){
        // пока исходим из того, что оба Register одного типа



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

    public static void sub(Register value1, Register value2){

    }
}
