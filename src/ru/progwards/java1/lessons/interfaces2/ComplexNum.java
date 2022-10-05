package ru.progwards.java1.lessons.interfaces2;

public class ComplexNum implements ToString {

    int a, b;


    public ComplexNum(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public String toString() {
        return a + "+" + b + "i";
    }

    public ComplexNum add(ComplexNum num) {
        //(a + bi) + (c + di) = (a + c) + (b + d)i
        return new ComplexNum((a + num.a), (b + num.b));
    }

    public ComplexNum sub(ComplexNum num) {
        //(a + bi) - (c + di) = (a - c) + (b - d)i
        return new ComplexNum((a - num.a), (b - num.b));
    }

    public ComplexNum mul(ComplexNum num) {
        //(a + bi) * (c + di) = (a*c - b*d) + (b*c + a*d)i
        return new ComplexNum((a * num.a - b * num.b), (b * num.a + a * num.b));
    }

    public ComplexNum div(ComplexNum num) {
        //(a + bi) / (c + di) = (a*c + b*d)/(c*c+d*d) + ((b*c - a*d)/(c*c+d*d))i
        return new ComplexNum(((a * num.a + b * num.b) / (num.a * num.a + num.b * num.b)), ((b * num.a - a * num.b) / (num.a * num.a + num.b * num.b)));
    }

    public static void main(String[] args) {
        ComplexNum cNum = new ComplexNum(11, 56);
        ComplexNum cNum2 = new ComplexNum(7, 16);
        System.out.println(cNum.toString());
        System.out.println(cNum2.toString());
        System.out.println(cNum.add(cNum2).toString());
        System.out.println(cNum.sub(cNum2).toString());
        System.out.println(cNum.mul(cNum2).toString());
        System.out.println(cNum.div(cNum2).toString());
    }

    @Override
    public String getString() {
        return toString();
    }
}
