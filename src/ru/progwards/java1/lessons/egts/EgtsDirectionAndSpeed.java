package ru.progwards.java1.lessons.egts;

public class EgtsDirectionAndSpeed {
//    byte dirLow;
//    short speedAndDir;



    public static int getSpeed(short speedAndDir) {
        return speedAndDir & 0b00000000_00000000_01111111_11111111;
    }

    public static int getDirection(byte dirLow, short speedAndDir) {
        int ninthBit = (speedAndDir & 0b00000000_00000000_10000000_00000000) >> 8;
        int eighthBit = dirLow & 0b00000000_00000000_00000000_10000000;
        int firstSevenBits = dirLow & 0b00000000_00000000_00000000_01111111;

        return ninthBit + eighthBit + firstSevenBits;
    }

    public static void main(String[] args) {

        System.out.println(getDirection((byte) 0b10001010, (short) 0b10011100_00111010));
        System.out.println(getSpeed((short) 0b10011100_00111010));

    }
}
