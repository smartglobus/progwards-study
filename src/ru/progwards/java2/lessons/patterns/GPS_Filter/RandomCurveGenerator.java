package ru.progwards.java2.lessons.patterns.GPS_Filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomCurveGenerator {
    private static List<Double> curvePoints = new ArrayList<>();

    static List<Double> tripleSinus() {
        Random random = new Random();

        int r1 = random.nextInt(15) - 8;
        int r2 = random.nextInt(15) - 8;
        int r3 = random.nextInt(15) - 8;
        double d1 = random.nextDouble();
        double d2 = random.nextDouble();
        double d3 = random.nextDouble();

        for (int i = 1000; i > 0; i--) {
            double rnd1 = Math.sin(i * d1 + r2);
            double rnd2 = Math.sin(i * d2 + r3);
            double rnd3 = Math.sin(i * d3 + r1);
            curvePoints.add(rnd1 + rnd2 + rnd3 + 3 + random.nextDouble() / 2);// диапазон 0...6.5
        }
        return curvePoints;
    }

    public static void main(String[] args) {
        for (Double p : tripleSinus()) {
            int spaces = (int) (p * 10);
            for (int i = 0; i < spaces; i++) {
                System.out.print(" ");
            }
            System.out.println("*****\n");
        }
    }
}
