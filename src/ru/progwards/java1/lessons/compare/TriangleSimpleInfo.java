package ru.progwards.java1.lessons.compare;

public class TriangleSimpleInfo {

    public static int maxSide(int a, int b, int c){
        int biggerSide = a;
        if (b>a)
            biggerSide = b;
        if (c>biggerSide)
            return c;
        return biggerSide;

    }
    public static void main(String[] args) {
        System.out.println(maxSide(1,2,3));
        System.out.println(maxSide(2,1,3));
        System.out.println(maxSide(3,2,1));
        System.out.println(maxSide(0,0,0));
    }
}
