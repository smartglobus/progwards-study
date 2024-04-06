package ru.progwards.java2.lessons.patterns.GPS_Filter;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GPS_Points_Source {
    private Queue<Double> speedRecord;// speeds, m/s
    private final static long COUNT_INTERVAL = 1000;// GPS count interval, milliseconds
    private final static int OneDegreeDistance = 111 * 1000;// примерное расстояние в 1 градусе широты или долготы, м
    private Random random = new Random();
    private double direction = 1;// initial direction, radians
    private double lat = 50.11322027819375;
    private double lon = 14.429022682036347;
    private long time = System.currentTimeMillis();

    public GPS_Points_Source() {
        speedRecord = new ArrayDeque<>(RandomCurveGenerator.tripleSinus());
    }

    public boolean hasNextPoint() {
        return !speedRecord.isEmpty();
    }

    public GPS getNextPoint() {
        if (speedRecord.isEmpty()) return null;
        double lastSpeed = speedRecord.poll();

        // выброс GPS-бага
        if (ThreadLocalRandom.current().nextInt() % 20 == 1) {
            time += COUNT_INTERVAL / 2;// поправка " / 2" корректно работает при выбросе одного GPS-бага подряд
            return new GPS(lat + random.nextDouble() * 0.0001, lon + random.nextDouble() * 0.0001, time);
        }

        double lastDistance = lastSpeed * COUNT_INTERVAL / 1000;
        direction += ((random.nextDouble() - 0.5) / 5);
        lat += lastDistance * Math.sin(direction) / OneDegreeDistance;
        lon += lastDistance * Math.cos(direction) / OneDegreeDistance;
        time += COUNT_INTERVAL;

        return new GPS(lat, lon, time);
    }
}
