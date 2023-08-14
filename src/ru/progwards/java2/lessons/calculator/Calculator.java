package ru.progwards.java2.lessons.calculator;

public class Calculator {
    String expr;
    int pos;


    Calculator(String expression) {
        expr = expression;
        pos = 0;
    }

    boolean hasNext() {
        return pos < expr.length();
    }

    String getSym() {
        return expr.substring(pos, ++pos);
    }

    String chkNxtSym() {
        return expr.substring(pos, pos + 1);
    }


    public int sum() {
        int res = mult();

        while (hasNext()) {
            String nxtOp = getSym();
            if ("+-".contains(nxtOp)) {
                switch (nxtOp) {
                    case "+":
                        res += mult();
                        break;
                    case "-":
                        res -= mult();
                        break;
                }
            }
        }
        return res;
    }

    int mult() {
        // возвращает или результат */ , или просто цифру
        int res = Integer.parseInt(getSym());
        while (hasNext()) {
            String nxtOp = chkNxtSym();
            if ("*/".contains(nxtOp)) {
                pos++;
                int nxtNum = Integer.parseInt(getSym());
                switch (nxtOp){
                    case "*":
                        res *=  nxtNum;
                        break;
                    case "/":
                        res /=  nxtNum;
                        break;
                }
            } else return res;
        }
        return res;
    }


    public static int calculate(String expression) {
        return new Calculator(expression).sum();
    }

    public static void main(String[] args) {
        System.out.println(calculate("2+8*6/4+3+5-3*2"));
    }
}
