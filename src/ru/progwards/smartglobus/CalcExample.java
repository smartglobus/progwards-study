package ru.progwards.smartglobus;

public class CalcExample {
    String expr;
    int pos;

    boolean hasNex() {
        return pos < expr.length();
    }

    String getSym() throws Exception {// метод отвечает за итерацию по строке
        if (!hasNex())
            throw new Exception("unexpected end");
        return expr.substring(pos, ++pos);
    }

    String checkSym() throws Exception {
        if (!hasNex())
            throw new Exception("unexpected end");
        return expr.substring(pos,pos+1);
    }

    int getNum() throws Exception {
        return Integer.parseInt(getSym());
    }

    String getOperation() throws Exception {
        return getSym();
    }

    String checkOperation() throws Exception {
        return checkSym();
    }

    public CalcExample(String expr) {
        this.expr = expr;
        pos = 0;
    }
    // считает результат выражений в скобках, или возвращает следующий символ
    int term2() throws Exception {
        if (checkSym().equals("(")) {
            getSym();// шаг на один символ
            int num = expresion();
            if (!getSym().equals(")"))
                throw new Exception(") expected");
            return num;// результат в скобках
        }
        else
            return getNum();// просто текущая цифра
    }
// обработка умножения и деления
    int term() throws Exception {
        int result = term2();
        while (hasNex()) {
            String op = checkOperation();
            if ("*/".contains(op)) {
                getOperation(); // шаг на один символ
                int num = term2();
                switch (op) {
                    case "*":
                        result *= num;
                        break;
                    case "/":
                        result /= num;
                        break;
                }
            }
            else
                return result;
        }
        return result;
    }

    int expresion() throws Exception {
        int result = term();
        while (hasNex()) {
            String op = checkOperation();
            if ("+-".contains(op)) {
                getOperation();
                int num = term();
                switch (op) {
                    case "+":
                        result += num;
                        break;
                    case "-":
                        result -= num;
                        break;
                }
            }
            else
                return result;
        }
        return result;
    }

    // expression ::= number { (+-*/) number }

    // expression ::= term ("+"|"-") term
    // term ::= num ("*"|"/") num

    // expression ::= term ("+"|"-") term
    // term ::= temr2 ("*"|"/") term2
    // term2 ::= "(" expression ")" | number

    static int calculate(String expr)  throws Exception {
        return new CalcExample(expr).expresion();
    }

    public static void main(String[] args) throws Exception {
        calculate("2+5");
        System.out.println(calculate("5+(2+2)/3+(3/2"));
    }
}
