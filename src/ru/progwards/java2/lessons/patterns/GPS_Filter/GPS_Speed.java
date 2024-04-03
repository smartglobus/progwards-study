package ru.progwards.java2.lessons.patterns.GPS_Filter;

public class GPS_Speed implements SpeedCalculator {
    private GPS lastPos;

    @Override
    public double getSpeed(GPS newPos) {
        double speed = getSpeed(lastPos, newPos);
        lastPos = newPos;
        return speed;
    }
}
