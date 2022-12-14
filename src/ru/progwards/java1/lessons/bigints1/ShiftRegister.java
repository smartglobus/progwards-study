package ru.progwards.java1.lessons.bigints1;

public class ShiftRegister {

    public static void left(Register value) {
        for (int i = value.regVolume - 1; i > 0; i--) {
            value.register[i].value = value.register[i - 1].value;
        }
        value.register[0].value = false;
    }

    public static void right(Register value) {
        for (int i = 0; i < value.regVolume - 1; i++) {
            value.register[i].value = value.register[i + 1].value;
        }
        value.register[value.regVolume - 1].value = false;
    }
}
