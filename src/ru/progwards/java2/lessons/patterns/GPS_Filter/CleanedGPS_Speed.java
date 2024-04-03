package ru.progwards.java2.lessons.patterns.GPS_Filter;

import java.util.ArrayDeque;
import java.util.Deque;

public class CleanedGPS_Speed implements SpeedCalculator {
    private GPS_Speed speedometer;
    private Deque<Double> last50speeds = new ArrayDeque<>();
    private GPS lastPos;
    private double lastE = 0;
    private double lastD = 0;

    public CleanedGPS_Speed(GPS_Speed speedometer) {
        this.speedometer = speedometer;
    }

    private void saveLastSpeed(double speed) {
        last50speeds.addLast(speed);
        if (last50speeds.size() > 50)
            last50speeds.removeFirst();
    }

    private boolean checkED(double speed) {
        int n = last50speeds.size();
        if (n < 50 || Math.abs(lastE - speed) <= Math.sqrt(lastD) * 3) {
            double newE = (lastE * n + speed) / (n + 1);
            lastD = ((((lastD + (lastE * lastE)) * n) + (speed * speed)) / (n + 1)) - (newE * newE);
            lastE = newE;
            return true;
        }
        return false;
    }


    @Override
    public double getSpeed(GPS newPos) {
        // проброс первого отсчёта, чтобы не портить статистику
        if (lastPos == null) {
            lastPos = newPos;
            return 0;
        }

        double speed = speedometer.getSpeed(lastPos, newPos);
        if (last50speeds.size() < 50) {
            checkED(speed);
            saveLastSpeed(speed);
            lastPos = newPos;
        } else {
            if (checkED(speed)) {
                saveLastSpeed(speed);
                lastPos = newPos;
            } else {
                speed = last50speeds.peekLast();
            }
        }
        return speed;
    }
}
