package ru.progwards.java1.lessons.compare;

public class TriangleInfo {

    public static boolean isTriangle(int a, int b, int c) {
        if (a < (b + c) & b < (a + c) & c < (a + b))
            return true;
        return false;
    }

    public static boolean isRightTriangle(int a, int b, int c) {
        if (RightTriangleCheck(a,b,c) | RightTriangleCheck(b,a,c) | RightTriangleCheck(c,a,b))
            return true;
        return false;
    }

public static boolean RightTriangleCheck(int x, int y, int z){
    if (x * x == (y * y + z * z))
        return true;
    return false;
}

    public static boolean isIsoscelesTriangle(int a, int b, int c) {
        if (a == b | b == c | a == c)
            return true;
        return false;
    }

    public static void main(String[] args) {
        System.out.println(isTriangle(2, 2, 3));
        System.out.println(isTriangle(2, 3, 4));
        System.out.println(isTriangle(3, 2, 5));
        System.out.println(isTriangle(2, 3, 6));
        System.out.println(isTriangle(3, 3, 9));

        System.out.println(isRightTriangle(2, 3, 4));
        System.out.println(isRightTriangle(3, 4, 5));

        System.out.println(isIsoscelesTriangle(3, 4, 5));
        System.out.println(isIsoscelesTriangle(2, 2, 3));
        System.out.println(isIsoscelesTriangle(2, 3, 2));
        System.out.println(isIsoscelesTriangle(3, 2, 2));
        System.out.println(isIsoscelesTriangle(2, 2, 2));
        System.out.println(isIsoscelesTriangle(3, 3, 3));
    }
}
