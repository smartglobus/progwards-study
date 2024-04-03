package ru.progwards.java2.lessons.patterns.GPS_Filter;

public interface SpeedCalculator {
    double getSpeed(GPS newPos);

    default double getSpeed(GPS lastPos, GPS newPos) {
        if (lastPos == null) {
            return 0;
        }
        double dLat = newPos.lat - lastPos.lat;
        double dLon = newPos.lon - lastPos.lon;
        long dTime = newPos.time - lastPos.time;

        return (Math.sqrt(dLat * dLat + dLon * dLon)) / dTime;
    }
}
