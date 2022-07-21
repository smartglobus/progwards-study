package ru.progwards.java1.lessons.compare;

public class TriangleInfo {

    public static boolean isTriangle(int a, int b, int c) {
        // int biggerSide = TriangleSimpleInfo.maxSide(a, b, c);
        if (a < (b + c) & b < (a + c) & c < (a + b))
            return true;
        return false;
    }

    public static void main(String[] args) {
        System.out.println(isTriangle(2, 2, 3));
        System.out.println(isTriangle(2, 3, 4));
        System.out.println(isTriangle(3, 2, 5));
        System.out.println(isTriangle(2, 3, 6));
        System.out.println(isTriangle(3, 3, 9));
    }
}
