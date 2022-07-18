package ru.progwards.java1.lessons.basics;

public class ReverseDigits {
    public static int reverseDigits(int number){
        int hundreds = number%1000/100;
        int dozens = number%100/10;
        int digits = number%10;
        return digits*100 + dozens*10 + hundreds;
    }

    public static void main(String[] args) {
        System.out.println(reverseDigits(835));
    }
}
