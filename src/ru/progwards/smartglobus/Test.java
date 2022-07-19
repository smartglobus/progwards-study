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
        int t = (int)765.567;
        System.out.println((short)2147478071);
        System.out.println(maxInt());
        System.out.println(maxInt()+2147483647+2147483647+2147483647+4);
        //boolean t = Character.isLetter('h');
        System.out.println((short) 256567);
        String twoInt =t+""+t;
        int z = Integer.parseInt(twoInt);
        System.out.println(addAsStrings(56,976));
    }

    static int addAsStrings(int n1, int n2){
        String twoInt =n1 + "" + n2;
        return Integer.parseInt(twoInt);
    }

    static int maxInt() {
        return MAX_VALUE;
    }

    static int toInt(String str) {
        return Integer.parseInt(str);
    }
}
