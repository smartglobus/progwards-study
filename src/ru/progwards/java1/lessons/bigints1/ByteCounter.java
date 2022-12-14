package ru.progwards.java1.lessons.bigints1;

public class ByteCounter extends Counter {
    public static void inc(Register value) {
//            for (int i = 0; i < 8; i++) {
//                value.eightBits[i].value = !value.eightBits[i].value;
//                if (value.eightBits[i].value) break;
//            }
        Counter.inc(value);
    }

    public static void dec(Register value) {
//            for (int i = 0; i < 8; i++) {
//                value.eightBits[i].value = !value.eightBits[i].value;
//                if (!value.eightBits[i].value) break;
//            }
        Counter.dec(value);
    }
}
