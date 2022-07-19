package ru.progwards.java1.lessons.custing;

public class OneLine {

    public static String strValue(String value) {
        return "Передана строка со значением " + value;
    }

    public static String intValue(String variable, int value) {
        return "Значение " + variable + " равно " + value;
    }

    public static void main(String[] args) {
        System.out.println(strValue("высокой важности"));
        System.out.println(intValue("высокой важности", 100500));
    }
}
