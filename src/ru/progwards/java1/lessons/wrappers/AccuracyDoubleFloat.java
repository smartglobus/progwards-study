package ru.progwards.java1.lessons.wrappers;

public class AccuracyDoubleFloat {
    static final double R_D = 6371.2;
    static final float R_F = 6371.2f;
    static final double PI_D = 3.14;
    static final float PI_F = 3.14f;

    public static Double volumeBallDouble(Double radius) {
        return radius * radius * radius * PI_D * 4 / 3;
    }

    public static Float volumeBallFloat(Float radius) {
        return radius * radius * radius * PI_F * 4 / 3;
    }

    public static Double calculateAccuracy(Double radius) {
        return volumeBallDouble(radius) - volumeBallFloat(radius.floatValue());
    }

    public static void main(String[] args) {
        System.out.println(volumeBallDouble(R_D));
        System.out.println(volumeBallFloat(R_F));
        System.out.println(calculateAccuracy(R_D));
        String s1 = "Сложим строки";
        String s2 = "и так и сяк";
        String s3 = "потому что";
        String s4 = "мы можем всяк";

        String str = s1 + " " + s2;
        System.out.println(str + ", " + s3.concat(" ".concat(s4).concat(".")));
    }
}
