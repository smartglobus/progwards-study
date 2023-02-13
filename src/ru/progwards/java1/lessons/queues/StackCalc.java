package ru.progwards.java1.lessons.queues;

import java.util.ArrayDeque;
import java.util.Deque;

public class StackCalc {
    public static Deque<Double> stack = new ArrayDeque<>();

    public static void push(double value) {
        stack.push(value);
//        System.out.println(stack);
    }

    public static double pop() {
        return stack.pop();
    }

    // сложить 2 верхних значения на стеке, результат положить на стек. В итогу в стеке должно быть на один элемент меньше
    public static void add() {
        stack.push(stack.pop() + stack.pop());
    }

    // вычесть верхнее значение на стеке, из следующего по глубине, результат положить на стек. В итоге в стеке должно быть на один элемент меньше
    public void sub() {
        stack.push(-stack.pop() + stack.pop());
    }

    // умножить 2 верхних значения на стеке, результат положить на стек. В итогу в стеке должно быть на один элемент меньше
    public static void mul() {
        stack.push(stack.pop() * stack.pop());
    }

    // поделить на верхнее значение на стеке, следующее по глубине, результат положить на стек. В итоге в стеке должно быть на один элемент меньше
    public void div() {
        double d = stack.pop();
        stack.push(stack.pop() / d);
    }

}

class Calculate {

    public static double calculation1() {

StackCalc.push(2.2);
        StackCalc.push(2.2);
        StackCalc.push(3d);
        StackCalc.push(12.1);

        StackCalc.add();
        StackCalc.mul();

        return StackCalc.pop();
    }

    public static double calculation2() {

        return 0;
    }

    public static void main(String[] args) {
        System.out.println(calculation1());
//        for (double d: StackCalc.stack) System.out.println(d);
    }
}
