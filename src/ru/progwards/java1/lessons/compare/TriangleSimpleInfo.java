package ru.progwards.java1.lessons.compare;

public class TriangleSimpleInfo {

    public static int maxSide(int a, int b, int c) {
        int biggerSide = a;
        if (b > a)
            biggerSide = b;
        if (c > biggerSide)
            return c;
        return biggerSide;
    }

    public static int minSide(int a, int b, int c) {
        int minorSide = a;
        if (b < a)
            minorSide = b;
        if (c < minorSide)
            return c;
        return minorSide;
    }

    public static boolean isEquilateralTriangle(int a, int b, int c) {
        if (a == b && b == c)
            return true;
        return false;
    }

    public static void main(String[] args) {
        System.out.println(maxSide(1, 2, 3));
        System.out.println(maxSide(2, 1, 3));
        System.out.println(maxSide(3, 2, 1));
        System.out.println(maxSide(0, 0, 0));

        System.out.println(minSide(1, 2, 3));
        System.out.println(minSide(2, 1, 3));
        System.out.println(minSide(3, 2, 1));
        System.out.println(minSide(0, 0, 0));

        System.out.println(isEquilateralTriangle(2, 2, 3));
        System.out.println(isEquilateralTriangle(2, 3, 2));
        System.out.println(isEquilateralTriangle(3, 2, 2));
        System.out.println(isEquilateralTriangle(2, 2, 2));
        System.out.println(isEquilateralTriangle(3, 3, 3));
    }
}
