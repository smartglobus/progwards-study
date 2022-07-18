package ru.progwards.smartglobus;

import static java.lang.Integer.MAX_VALUE;

public class Test {
    static String a = "3пролл  'о'ор'  оро";

    public static void main(String[] args) {
       long tt = 123;
        { String a = "2"; }
        String a = "3пролл  'о'ор'  оро";
        String n = "1\" 5845" + 5 + 8;
        Integer y = 5;
        Double d = 2.;
        int i1 = 5;
        double d1 = Double.parseDouble("7.998786");
        Integer i = Integer.parseInt("8");
         i1 = MAX_VALUE;
       // System.out.println(i);
        System.out.println(maxInt());
        boolean t = Character.isLetter('h');
        System.out.println(t);
    }
    static int maxInt(){
        return Integer.MAX_VALUE;
    }
    static int toInt(String str){
        return Integer.parseInt(str);
    }
}
