package ru.progwards.java2.lessons.patterns.GPS_Filter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

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
        if (newPos == null) throw new NullPointerException("GPS signal is lost");

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
                System.out.printf("Skipping suspicious GPS point! Speed = %.2f\n", speed);
                speed = last50speeds.peekLast();// возврат последнего значения скорости
            }
        }
        return speed;
    }

    public static void main(String[] args) {
        GPS_Points_Source pointsSource = new GPS_Points_Source();

        List<GPS> gpsList = new ArrayList<>();
        while (pointsSource.hasNextPoint()) {
            gpsList.add(pointsSource.getNextPoint());
        }
        gpsList.forEach(System.out::println);
    }
}
