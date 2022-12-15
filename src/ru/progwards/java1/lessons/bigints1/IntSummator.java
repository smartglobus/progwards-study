package ru.progwards.java1.lessons.bigints1;

public class IntSummator extends Summator{
    public static void add(IntRegister value1, IntRegister value2) {
        Summator.add(value1, value2);
    }

    public static void sub(IntRegister value1, IntRegister value2) {
        Summator.sub(value1,value2);
    }


    public static void main(String[] args) {
        IntRegister one = new IntRegister(25);
        IntRegister two = new IntRegister(-50);
        sub(one, two);
        System.out.println(one);
        System.out.println(one.toDecString());
        System.out.println(Integer.toBinaryString(17 + 256 * 256 * 256 * 128));

        System.out.println(Integer.toBinaryString(-9));

        System.out.println(Integer.toBinaryString(17 - 9));
    }
}
