package ru.progwards.java2.lessons.patterns.GPS_Filter;

public class GPS_Speed implements SpeedCalculator {
    private GPS lastPos;

    @Override
    public double getSpeed(GPS newPos) {
        if (newPos==null)throw new NullPointerException("GPS signal is lost");
        double speed = getSpeed(lastPos, newPos);
        lastPos = newPos;
        return speed;
    }
}
