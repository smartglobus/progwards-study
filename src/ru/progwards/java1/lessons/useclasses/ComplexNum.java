package ru.progwards.java1.lessons.useclasses;

public class ComplexNum {

    int a, b;


    public ComplexNum(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public String toString() {
        return a + "+" + b + "i";
    }

    public ComplexNum add(ComplexNum num) {
        ComplexNum add = new ComplexNum((this.a + num.a), (this.b + num.b));
        //(a + bi) + (c + di) = (a + c) + (b + d)i
        return add;
    }

    public ComplexNum sub(ComplexNum num) {
        ComplexNum sub = new ComplexNum((this.a - num.a), (this.b - num.b));
        //(a + bi) - (c + di) = (a - c) + (b - d)i
        return sub;
    }

    public ComplexNum mul(ComplexNum num) {
        //(a + bi) * (c + di) = (a*c - b*d) + (b*c + a*d)i
        return new ComplexNum((this.a * num.a - this.b * num.b), (this.b * num.a + this.a * num.b));
    }

    public ComplexNum div(ComplexNum num) {
        ComplexNum div = new ComplexNum(((this.a * num.a + this.b * num.b) / (num.a * num.a + num.b * num.b)), ((this.b * num.a - this.a * num.b) / (num.a * num.a + num.b * num.b)));
        //(a + bi) / (c + di) = (a*c + b*d)/(c*c+d*d) + ((b*c - a*d)/(c*c+d*d))i
        return div;
    }

    public static void main(String[] args) {
        ComplexNum cNum = new ComplexNum(11, 56);
        ComplexNum cNum2 = new ComplexNum(7, 16);
        System.out.println(cNum.toString());
        System.out.println(cNum2.toString());
        System.out.println(cNum.add(cNum2).toString());
        System.out.println(cNum.sub(cNum2).toString());
        System.out.println(cNum.mul(cNum2).toString());
        System.out.println(cNum.div(cNum2));
    }

}
