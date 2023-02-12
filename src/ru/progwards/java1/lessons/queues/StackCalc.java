package ru.progwards.java1.lessons.queues;

import java.util.ArrayDeque;
import java.util.Deque;

public class StackCalc {
    Deque<Double> stack = new ArrayDeque<>();

    public void push(double value) {
        stack.push(value);
    }

    public double pop() {
        return stack.pop();
    }

    // сложить 2 верхних значения на стеке, результат положить на стек. В итогу в стеке должно быть на один элемент меньше
    public void add() {
        stack.push(stack.pop() + stack.pop());
    }

    // вычесть верхнее значение на стеке, из следующего по глубине, результат положить на стек. В итоге в стеке должно быть на один элемент меньше
    public void sub() {
        stack.push(-stack.pop() + stack.pop());
    }

    // умножить 2 верхних значения на стеке, результат положить на стек. В итогу в стеке должно быть на один элемент меньше
    public void mul() {
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

        return 0;
    }

    public static double calculation2() {

        return 0;
    }
}
