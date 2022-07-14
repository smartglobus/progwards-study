package ru.progwards.smartglobus;

public class Variables {
     static int sum = 0;


        public static void increment(int n){
            sum = sum + n;
        }

        public static void decrement ( int n){
            sum = sum - n;
        }

        public static void printSum () {
            System.out.println("sum = " + sum);
        }

    public static void calculation(){
        increment(12);
        printSum();
        decrement(8);
        printSum();
        increment(-7);
        printSum();
    }


    public static void roomParameters(String room, int length, int width){
        int floor = length * width;
        int perimeter = 2 * (length + width);
        int height = 3;
        int walls = perimeter * height;
        System.out.println("Комната " + room + ", нужно линолеума " + floor + " м2, обоев " + walls + " м2, при высоте потолков " + height + " м");
    }

    public static void apartment(){
        roomParameters("гостинная", 4, 5);
        roomParameters("кухня", 3, 3);
        roomParameters("спальня", 3, 4);
        roomParameters("прихожая", 2, 2);
    }


    public static String getFirst(){
            return "Не тот велик, ";
    }
    public static String getSecond(){
            return "кто никогда не падал, ";
    }
    public static String getThird(){
            return "а тот велик — ";
    }
    public static String getForth(){
            return "кто падал и вставал!. ";
    }
    public static String getFifth(){
            return "Конфуций";
    }

    public static void printText(){
        String text = getFirst();
        System.out.println(text);
        text = text + getSecond();
        System.out.println(text);
        String text2 = getThird() + getForth();
        System.out.println(text2);
        System.out.println(getFifth());
        System.out.println(text + text2 + getFifth());
    }
}
