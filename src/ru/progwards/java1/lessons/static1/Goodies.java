package ru.progwards.java1.lessons.static1;

public class Goodies {
    static String goodies;
    static String child;

    public static void printPresent(){
        System.out.println(child + " дали " + goodies);
    }

    public static void masha(){
        child = "Маше";
    }

    public static void cookie(){
        goodies = "печеньку";
    }

    public static void setChild(String child){
       Goodies.child = child;
    }

    public static void setGoodies(String goodies){
        Goodies.goodies = goodies;
    }

    public static void printPresents(){
        masha();
        printPresent();
        setGoodies("леденец");
        printPresent();
        cookie();
        printPresent();
        setChild("Пете");
        printPresent();
    }

    public static void main(String[] args){
        printPresents();
    }

}
