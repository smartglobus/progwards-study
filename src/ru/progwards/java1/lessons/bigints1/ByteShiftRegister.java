package ru.progwards.java1.lessons.bigints1;

public class ByteShiftRegister extends ShiftRegister {
    public static void left(Register value) {
//            for (int i = 7; i > 0; i--) {
//                value.eightBits[i].value = value.eightBits[i - 1].value;
//            }
//            value.eightBits[0].value = false;
        ShiftRegister.left(value);
    }

    public static void right(Register value) {
//            for (int i = 0; i < 7; i++) {
//                value.eightBits[i].value = value.eightBits[i + 1].value;
//            }
//            value.eightBits[7].value = true;
        ShiftRegister.right(value);
    }
}
