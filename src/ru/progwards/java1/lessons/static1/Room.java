package ru.progwards.java1.lessons.static1;

public class Room {
    static double length1 = 5;
    static double width1 = 3;
    static double length2 = length1 + 1.5;
    static double width2 = width1 * 1.5;
    static double square1 = square(length1, width1);
    static double square2 = square(length2, width2);

    public static double square(double length, double width){
        return length * width;
    }

    public static void printRoom(String name, double square){
        System.out.println("Площадь " + name + " составляет " + square + " м2");
    }

    public static void printFlat(){
        printRoom("кухни", square1);
        printRoom("гостинной", square2);

    }

    public static void main(String[] args){
        printFlat();
    }
}
