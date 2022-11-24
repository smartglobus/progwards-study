package ru.progwards.java1.lessons.bitsworld;

public class CheckBit {
    public static int checkBit(byte value, int bitNumber) {
        int bitValue = 0;
        if ((value >> bitNumber) % 2 != 0) {
            bitValue = 1;
        }
        return bitValue;
    }

    public static void main(String[] args) {
        System.out.println(checkBit((byte) -124, 7));

    }
}
