package ru.progwards.java2.lessons.patterns.GPS_Filter;

public class GPS {
    public double lat; // широта
    public double lon; // долгота
    public long time; // время в мс

    public GPS(double lat, double lon, long time) {
        this.lat = lat;
        this.lon = lon;
        this.time = time;
    }

    @Override
    public String toString() {
        return "GPS{" +
                "lat=" + lat +
                ", lon=" + lon +
                ", time=" + time +
                '}';
    }
}
