package ru.progwards.java2.lessons.tests.calc;

public class SimpleCalculator {

    public int sum(int a, int b) throws ArithmeticException {
        long res = (long) a + b;
        if (res > Integer.MAX_VALUE || res < Integer.MIN_VALUE)
            throw new ArithmeticException("Переполнение диапазона int в методе sum.");
        return (int) res;
    }


    public int diff(int a, int b) throws ArithmeticException {
        try {
            return sum(a, -b);
        } catch (ArithmeticException e) {
            throw new ArithmeticException("Переполнение диапазона int в методе sum, вызванного из метода diff");
        }

    }


    public int mult(int a, int b) throws ArithmeticException {
        long res = (long) a * b;
        if (res > Integer.MAX_VALUE || res < Integer.MIN_VALUE)
            throw new ArithmeticException("Переполнение диапазона int в методе mult.");
        return (int) res;
    }


    public int div(int a, int b) throws ArithmeticException {
        if (b == 0) throw new ArithmeticException("Деление на ноль недопустимо.");
        return a / b;
    }
}
