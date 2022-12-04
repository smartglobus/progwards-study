package ru.progwards.java1.lessons.bigints1;

import java.util.Arrays;

public class Bit {
    boolean value;

    public Bit() {
    }

    public Bit(boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value ? "1" : "0";
    }


    class ByteRegister extends Bit {
        Bit[] eightBits = new Bit[8];

        public ByteRegister() {
            Arrays.fill(eightBits, new Bit(false));
        }

        public ByteRegister(byte value) {
            for (int i = 0, j = 1; i < 8; i++, j <<= 1) {
                if ((value & j) == 1){
                    eightBits[i].value = true;
                }
            }
        }


    }
}
