package ru.progwards.java1.lessons.bitsworld;

public class SumBits {
    public static int sumBits(byte value) {
        int result = 0;
        byte denominator = 1;
        for (int i = 0; i < 8; i++) {

            if ((value & denominator) != 0) {
                result++;
            }
            denominator <<= 1;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(sumBits((byte)0b10000000));
    }
}
