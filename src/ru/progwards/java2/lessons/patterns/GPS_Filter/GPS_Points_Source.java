package ru.progwards.java2.lessons.patterns.GPS_Filter;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

public class GPS_Points_Source {
    Queue<Double> speedRecord;// speed, m/s
    GPS startPoint;
    double lastSpeed;
    private final static long COUNT_INTERVAL = 100;// GPS count interval, milliseconds
    Random random = new Random();
    private double direction;// initial direction, radians
    private double lat = 50.11322027819375;
    private double lon = 14.429022682036347;
    private long time = System.currentTimeMillis();

    public GPS_Points_Source() {
        speedRecord = new ArrayDeque<>(RandomCurveGenerator.tripleSinus());
        direction = 1;
        startPoint = new GPS(lat, lon, time);
    }


    public GPS getNextPoint() {
        if (speedRecord.isEmpty()) return null;
        lastSpeed = speedRecord.poll();
        double lastDistance = lastSpeed * COUNT_INTERVAL / 1000;
        direction += ((random.nextDouble() - 0.5) / 5);
        lat += lastDistance * Math.sin(direction);
        lon += lastDistance * Math.cos(direction);
        time += COUNT_INTERVAL;

        return new GPS(lat, lon, time);
    }
}
