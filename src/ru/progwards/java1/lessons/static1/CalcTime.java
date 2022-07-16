package ru.progwards.java1.lessons.static1;

public class CalcTime {
    static double distance;
    static double speed;
    static String cityFrom;
    static String cityTo;

    public static void printTime(){
        double t = distance/speed;
        System.out.println("Если ехать из "+cityFrom+" в "+cityTo+" со скоростью "+speed+" км/ч, то "+distance+" км проедем за "+t+" часов.");
    }

    public static void main(String[] args){

    }
}
