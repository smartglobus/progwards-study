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

    String chkSym() {
        return expr.substring(pos, pos + 1);
    }

    public int sum() {
        int res = mult();

        while (hasNext()) {
            String nxtOp = chkSym();
            if ("+-".contains(nxtOp)) {
                pos++;
                int nxtNum =mult();
                if ("+".equals(nxtOp)) res += nxtNum;
                if ("-".equals(nxtOp)) res -= nxtNum;
            } else return res;
        }
        return res;
    }

    // Для варианта без обработки скобок вместо метода par() вызывается Integer.parseInt(getSym())
    int mult() {
        int res = par();// = Integer.parseInt(getSym());
        while (hasNext()) {
            String nxtOp = chkSym();
            if ("*/".contains(nxtOp)) {
                pos++;
                int nxtNum = par();// = Integer.parseInt(getSym());
                if ("*".equals(nxtOp)) res *= nxtNum;
                if ("/".equals(nxtOp)) res /= nxtNum;
            } else return res;
        }
        return res;
    }

    int par() {
        int res;
        if ("(".equals(chkSym())) {
            pos++;
            res = sum();
            if (")".equals(chkSym())) {
                pos++;
                return res;
            }
        }
        return Integer.parseInt(getSym());
    }

    public static int calculate(String expression) {
        return new Calculator(expression).sum();
    }

    public static void main(String[] args) {
        System.out.println(calculate("2+8*((6-4)*2-1)-3/2"));
    }
}
