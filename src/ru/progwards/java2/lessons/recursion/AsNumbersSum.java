package ru.progwards.java2.lessons.recursion;

import java.util.*;
import java.util.stream.Collectors;

public class AsNumbersSum {
    private static List<Integer> list = new ArrayList<>();
    private static int num;
    private static String str = "";

    public static String asNumbersSum(int number) {
        if (number < 2) return String.valueOf(number);
        if (number == 2) return "2 = 1+1";
        num = number;
        list.add(0, number - 1);
        list.add(1, 1);
        str += number + " = " + (number - 1) + "+" + 1 + " = " + func();
        return str.substring(0, str.length() - 2);
    }

    private static String func() {
        if (list.get(1) == 1) {
            int first = list.get(0) - 1;
            list.clear();
            fillList(first, num);
            str += list.stream().map(String::valueOf).collect(Collectors.joining("+")) + " = ";
            if (list.get(0) == 1) return str;
        }

        int n = list.stream().sorted().dropWhile(el -> el < 2).min(Comparator.comparingInt(el -> el)).get();
        int i = list.lastIndexOf(n);
        list.set(i, list.get(i) - 1);
        if (list.get(i) > 2 && i < list.size() - 1) list.set(i + 1, list.get(i + 1) + 1);
        else list.add(1);

        str += list.stream().map(String::valueOf).collect(Collectors.joining("+")) + " = ";
        return func();
    }

    private static void fillList(int n, int numExc) {
        list.add(n);
        numExc -= n;
        if (numExc - n > 0) {
            fillList(n, numExc);
        } else {
            list.add(numExc);
        }
    }

    public static void main(String[] args) {
        System.out.println(asNumbersSum(5));
    }
}