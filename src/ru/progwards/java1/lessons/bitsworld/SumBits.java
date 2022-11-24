package ru.progwards.java1.lessons.bitsworld;

public class SumBits {
    public static int sumBits(byte value) {
        int result = 0;
        byte denominator = 1;
        for (int i = 0; i < 7; i++) {
            if ((value & denominator) > 0) {
                result++;
            }
            denominator <<= 1;
        }
//        System.out.println(Integer.toBinaryString((byte)value));
        return result;
    }

    public static void main(String[] args) {
        System.out.println(sumBits((byte)127));
//        System.out.println(Integer.toBinaryString(Byte.MAX_VALUE));
    }
}
