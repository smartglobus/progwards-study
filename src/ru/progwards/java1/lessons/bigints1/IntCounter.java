package ru.progwards.java1.lessons.bigints1;

public class IntCounter extends Counter{
    public static void inc(Register value) {
        boolean signBefore = value.register[31].value;
        Counter.inc(value);
        if (!signBefore && value.register[31].value) { // если знаковый бит изменился с 0 на 1, результат стал 1000000.....
            value.register[31].value = false; // превращаем его в ноль
        }
    }

    public static void dec(Register value) {
        boolean signBefore = value.register[31].value;
        Counter.dec(value);
        if (signBefore && !value.register[31].value) { // если знаковый бит изменился с 1 на 0, результат стал 0111111111.....
            value.register[31].value = true; // превращаем его в единицу (зачем только?...)
        }
    }
}
