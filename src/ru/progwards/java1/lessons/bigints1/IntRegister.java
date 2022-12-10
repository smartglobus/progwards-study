package ru.progwards.java1.lessons.bigints1;

public class IntRegister extends Register{
    public Bit[] thirtyTwoBits;

    public IntRegister() {
        super(32);
        thirtyTwoBits = super.register;
    }

    public IntRegister(int value) {
        super(32,value);
        thirtyTwoBits = super.register;
    }

    @Override
    public String toDecString() {
        int result = thirtyTwoBits[30].value ? 1 : 0;

        for (int i = 29; i >= 0; i--) {
            result = result * 2 + (thirtyTwoBits[i].value ? 1 : 0);
        }

        if (thirtyTwoBits[31].value) {
            result = Integer.MIN_VALUE + result;
        }
        
        return Integer.toString(result);
    }

    public static void main(String[] args) {
        IntRegister a = new IntRegister(-120);
        System.out.println(Integer.toBinaryString(-120) + "   (check!)");
        System.out.println(a);
        System.out.println(a.toDecString());
    }
}
