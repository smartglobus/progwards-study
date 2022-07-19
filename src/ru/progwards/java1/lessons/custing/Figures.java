package ru.progwards.java1.lessons.custing;

public class Figures {

    public static double circle(double d) {
        return d * d * 3.14 / 4;
    }

    public static double square(double n) {
        return n * n;
    }

    public static Double triangle(double k) {
        double halfP = k * 3 / 2;
        return Math.sqrt(halfP * (halfP - k) * (halfP - k) * (halfP - k));
    }

    public static double squareVsTraiange(double p) {
        return square(p) / triangle(p);
    }

    public static double squareVsCircle(double p) {
        return square(p) / circle(p);
    }

    public static double triangleVsCircle(double p) {
        return triangle(p) / circle(p);
    }

    public static void main(String[] args) {
        System.out.println(circle(1));
        System.out.println(square(1));
        System.out.println(triangle(1));
        System.out.println(squareVsTraiange(6));
        System.out.println(squareVsCircle(138));
        System.out.println(triangleVsCircle(45.55));
    }
}
