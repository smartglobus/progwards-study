package ru.progwards.java2.lessons.patterns.GPS_Filter;

public interface SpeedCalculator {
    int OneDegreeDistance = 111 * 1000;// примерное расстояние в 1 градусе широты или долготы, м

    double getSpeed(GPS newPos);

    default double getSpeed(GPS lastPos, GPS newPos) {
        if (lastPos == null) {
            return 0;
        }
        double dLat = newPos.lat - lastPos.lat;
        double dY = dLat * OneDegreeDistance;
        double dLon = newPos.lon - lastPos.lon;
        double dX = dLon * OneDegreeDistance;
        long dTime = newPos.time - lastPos.time;

        return ((Math.sqrt(dX * dX + dY * dY)) / dTime) * 1000;
    }
}
