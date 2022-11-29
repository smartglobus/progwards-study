package ru.progwards.java1.lessons.bitsworld;

public class Binary {
    byte num;

    public Binary(byte num) {
        this.num = num;
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 7; i >= 0; i--) {
            result += CheckBit.checkBit(num, i);
        }
        return result;
    }

    public static void main(String[] args) {
        Binary test = new Binary((byte) 112);
        System.out.println(test);
    }
}
