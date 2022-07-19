package ru.progwards.smartglobus;

import static java.lang.Integer.MAX_VALUE;

public class Test {
    static String a = "3пролл  'о'ор'  оро";

    public static void main(String[] args) {
        long tt = 123;
        {
            String a = "2";
        }

        double d1 = Double.parseDouble("7.998786");
        Integer i = Integer.parseInt("8");

        System.out.println(4 / 3 * 2 * 5.7 / 2);
        System.out.println(maxInt());
        boolean t = Character.isLetter('h');
        System.out.println(d1);
    }

    static int maxInt() {
        return MAX_VALUE;
    }

    static int toInt(String str) {
        return Integer.parseInt(str);
    }
}
