package ru.progwards.java1.lessons.wrappers;

public class StringConverter {

    public static String fromByte(byte number){
        return Byte.toString(number);
    }

    public static String fromShort(short number){
        return Short.toString(number);
    }

    public static String fromInt(int number){
        return Integer.toString(number);
    }

    public static String fromLong(long number){
        return Long.toString(number);
    }

    public static String fromFloat(float number){
        return Float.toString(number);
    }

    public static String fromDouble(double number){
        return Double.toString(number);
    }


    public static void main(String[] args) {
        byte b = 67;
        System.out.println(fromByte(b));
        short s = 489;
        System.out.println(fromShort(s));
        System.out.println(fromInt(45555));
        System.out.println(fromLong(987L));
        System.out.println(fromFloat(3278.97f));
        System.out.println(fromDouble(68.93));
    }
}
