package ru.progwards.java1.lessons.bigints1;

public class IntRegister extends Register {
    public Bit[] thirtyTwoBits;

    public IntRegister() {
        super(32);
        thirtyTwoBits = super.register;
    }

    public IntRegister(int value) {
        super(32, value);
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

    public static class IntCounter extends Counter {

        public static void inc(Register value) {
            boolean signBefore = value.register[31].value;
            Counter.inc(value);
            if (!signBefore && value.register[31].value) { // если знаковый бит изменился с 0 на 1, результат стал 1000000.....
                value.register[31].value = false; // превращаем его в ноль
            }
        }

        public static void dec(Register value) {
            boolean signBefore = value.register[31].value;
            Counter.dec(value);
            if (signBefore && !value.register[31].value) { // если знаковый бит изменился с 1 на 0, результат стал 0111111111.....
                value.register[31].value = true; // превращаем его в единицу (зачем только?...)
            }
        }
    }

    public static class IntShiftRegister extends ShiftRegister {
        public static void left(Register value) {

            ShiftRegister.left(value);
        }

        public static void right(Register value) {

            ShiftRegister.right(value);
        }

    }

    public static class IntSummator{

    }

    public static void main(String[] args) {
        IntRegister a = new IntRegister(0b11100000_00010000_00000000_00000000);
//        System.out.println(Integer.toBinaryString(-120) + "   (check!)");
        System.out.println(a);
        System.out.println(a.toDecString());
        IntShiftRegister.right(a);
        System.out.println(a.toDecString());
        System.out.println(a);
    }
}
