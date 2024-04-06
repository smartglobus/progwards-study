package ru.progwards.java2.lessons.patterns.GPS_Filter;

public class GPS_Client {
    public static void main(String[] args) {
        GPS_Speed gpsSpeed = new GPS_Speed();
        CleanedGPS_Speed trueSpeed = new CleanedGPS_Speed(gpsSpeed);
        GPS_Points_Source pointsSource = new GPS_Points_Source();

        double currSpeed;

        for (int i = 1; true; i++) {
            GPS currPoint = pointsSource.getNextPoint();
            currSpeed = trueSpeed.getSpeed(currPoint);
            System.out.printf("Speed count %4d = %.1f\n", i, currSpeed);
        }
    }
}
