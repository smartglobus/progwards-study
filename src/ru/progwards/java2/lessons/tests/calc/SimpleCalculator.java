package ru.progwards.java2.lessons.tests.calc;

public class SimpleCalculator {

    public int sum(int a, int b) throws ArithmeticException {
        long res = (long) a + b;
        if (res > Integer.MAX_VALUE || res < Integer.MIN_VALUE)
            throw new ArithmeticException("Переполнение диапазона int.");
        return (int) res;
    }


    public int diff(int a, int b) throws ArithmeticException {
        long res = (long) a - b;
        if (res > Integer.MAX_VALUE || res < Integer.MIN_VALUE)
            throw new ArithmeticException("Переполнение диапазона int.");
//        return sum( a, - b);
        return (int) res;
    }


    public int mult(int a, int b) throws ArithmeticException {
        long res = (long) a * b;
        if (res > Integer.MAX_VALUE || res < Integer.MIN_VALUE)
            throw new ArithmeticException("Переполнение диапазона int.");
        return (int) res;
    }


    public int div(int a, int b) throws ArithmeticException {
        if (b == 0) throw new ArithmeticException("Деление на ноль недопустимо.");
        return a / b;
    }
}
