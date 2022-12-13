package ru.progwards.java1.lessons.bigints1;

public class Counter {

    public static void inc(Register value){
        for (int i = 0; i < value.regVolume; i++) {
            value.register[i].value = !value.register[i].value;
            if (value.register[i].value) break;
        }
    }

    public static void dec(Register value){
        for (int i = 0; i < value.regVolume; i++) {
            value.register[i].value = !value.register[i].value;
            if (!value.register[i].value) break;
        }
    }
}
