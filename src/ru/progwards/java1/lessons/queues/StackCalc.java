package ru.progwards.java1.lessons.queues;

import java.util.ArrayDeque;
import java.util.Deque;

public class StackCalc {
    private Deque<Double> stack;
    private double value;

    public StackCalc(){
        stack = new ArrayDeque<>();
    }

    public double getValue() {
        return value;
    }


    public void push(double value) {
        stack.push(value);
//        System.out.println(stack);
    }

    public double pop() {
        return stack.pop();
    }

    // сложить 2 верхних значения на стеке, результат положить на стек. В итогу в стеке должно быть на один элемент меньше
    public void add() {
        stack.push(stack.pop() + stack.pop());
    }

    // вычесть верхнее значение на стеке из следующего по глубине, результат положить на стек. В итоге в стеке должно быть на один элемент меньше
    public void sub() {
        stack.push(-stack.pop() + stack.pop());
    }

    // умножить 2 верхних значения на стеке, результат положить на стек. В итогу в стеке должно быть на один элемент меньше
    public void mul() {
        stack.push(stack.pop() * stack.pop());
    }

    // поделить второе по глубине значение на стеке на верхнее, результат положить на стек. В итоге в стеке должно быть на один элемент меньше
    public void div() {
        double d = stack.pop();
        stack.push(stack.pop() / d);
    }

}

class Calculate {


    public static double calculation1() {
        StackCalc calc1 = new StackCalc();

        calc1.push(2.2);
        calc1.push(3);
        calc1.push(12.1);

        calc1.add();
        calc1.mul();

        return calc1.pop();
    }

    // (737.22+24)/(55.6-12.1)+(19-3.33)*(87+2*(13.001-9.2))
    public static double calculation2() {
        StackCalc calc2 = new StackCalc();

        calc2.push(737.22);
        calc2.push(24);
        calc2.add();
        calc2.push(55.6);
        calc2.push(12.1);
        calc2.sub();
        calc2.div();
        calc2.push(19);
        calc2.push(3.33);
        calc2.sub();
        calc2.push(87);
        calc2.push(2);
        calc2.push(13.001);
        calc2.push(9.2);
        calc2.sub();
        calc2.mul();
        calc2.add();
        calc2.mul();
        calc2.add();

        return calc2.pop();
    }

    public static void main(String[] args) {
        System.out.println(calculation1());
        System.out.println(calculation2());
    }
}
