package ru.progwards.java1.lessons.params;

import java.util.Arrays;

public class FloatNumber {
    boolean sign;
    long mantissa;
    int exp;

    FloatNumber() {

    }

    FloatNumber(boolean sign, long mantissa, int exp) {
        this.sign = sign;
        this.mantissa = mantissa;
        this.exp = exp;
    }

    FloatNumber(String num) {

        char[] numToArray = num.toCharArray();

        // блок выделения знака
        if (numToArray[0] != '-') {
            sign = true;
        } else {
            sign = false;
        }

        // Нахождение ключевых точек
        long extrMnt = 0;
        int findDot = numToArray.length - 1; // первая значимая цифра
        int findE = numToArray.length - 1; // положение 'E'
        int lastNum = numToArray.length - 1; // последняя значимая цифра, или последняя цифра перед 'Е', если 'Е' обнаружится
        boolean isE = false; // проверка наличия 'E'
        int dotCount = 0; // учёт точки

        for (int i = 0; i < numToArray.length; i++) {

            if (numToArray[i] == 'e' || numToArray[i] == 'E') {
                findE = i;
                lastNum = i - 1;
                isE = true;
            }
            if (numToArray[i] == '.') {
                findDot = i;
            }
        }

        // если 'E' найдено, а точка нет
        if (findDot == numToArray.length - 1) {
            findDot = lastNum;
        }

        exp = findDot - lastNum;

        for (int i = 0; i <= lastNum; i++) {
// если extrMnt >= Long.MAX_VALUE перестать заполнять мантиссу и перейти к окончательному вычислению степени
            if (extrMnt >= Long.MAX_VALUE / 10) {
//коррекция exp по разнице между findDot и i на момент окончания заполнения мантиссы, c поправкой на возможный знак в начале
                exp = exp + lastNum + dotCount - i;
                break;
            }
            // не берёт в расчёт символы, не являющиеся цифрами ('-','+','.', и любые другие)
            if (numToArray[i] == '.') {
                dotCount++;
                continue;
            }
            if (Character.isDigit(numToArray[i])) {
                extrMnt = extrMnt * 10 + Character.digit(numToArray[i], 10);
            }
        }
        mantissa = extrMnt;

// блок поправки к ехр на основании значения 'E'
        boolean expSign = true;
        int addToExp = 0;
//если 'E' найдена, определяем знак степени
        if (isE) {

            if (numToArray[findE + 1] != '-') {
                expSign = true;
            } else {
                expSign = false;
            }

            for (int i = findE + 1; i < numToArray.length; i++) {
                if (!Character.isDigit(numToArray[i])) {
                    continue;
                }
                addToExp = addToExp * 10 + Character.digit(numToArray[i], 10);
            }
        }
        if (expSign) {
            exp += addToExp;
        } else {
            exp -= addToExp;
        }
    }

    @Override
    public String toString() {

        long mantissaFirstDigit = mantissa;
        char[] mantissaArray = Long.toString(mantissa).toCharArray();
        char[] mantissaWithoutFirstDigitArray = null;
        if (mantissaArray.length <= 1) {
            mantissaWithoutFirstDigitArray = new char[]{0};
            mantissaWithoutFirstDigitArray[0] = '0';
        } else {
            mantissaWithoutFirstDigitArray = new char[mantissaArray.length - 1];
            for (int i = 0; i < mantissaWithoutFirstDigitArray.length; i++) {
                mantissaWithoutFirstDigitArray[i] = mantissaArray[i + 1];
            }
        }
        int expCorrToStd = 0;

        while (mantissaFirstDigit >= 10) {
            mantissaFirstDigit /= 10;
            expCorrToStd++;
        }

// первая цифра плюс знак
        if (!sign) {
            mantissaFirstDigit *= -1;
        }

// если 'E' отлична от нуля
        if (exp + expCorrToStd != 0) {
            return mantissaFirstDigit + "." + charArrayToString(mantissaWithoutFirstDigitArray) + "E" + (exp + expCorrToStd);
        }
        return mantissaFirstDigit + "." + charArrayToString(mantissaWithoutFirstDigitArray);
    }

    String charArrayToString(char[] a) {
        String res = Character.toString(a[0]);
        for (int i = 1; i < a.length; i++) {
            res += Character.toString(a[i]);
        }
        return res;
    }

    double toDouble() {
        double res = (double) mantissa;
        if (!sign) {
            res *= -1;
        }
        if (exp >= 0) {
            for (int i = 0; i < exp; i++) {
                res *= 10;
            }
        }
        if (exp < 0) {
            for (int i = 0; i < -exp; i++) {
                res /= 10;
            }
        }
        return res;
    }

    void fromDouble(double num) {

        if (num >= 0) {
            sign = true;
        } else {
            //меняем знак
            num *= -1;
            sign = false;
        }

        double extractMnt = num;
        exp = 0;
        if (extractMnt > Long.MAX_VALUE) {
            while (true) {
                extractMnt /= 10;
                exp++;
                if (extractMnt < Long.MAX_VALUE) {
                    break;
                }
            }
        } else {
            while (extractMnt < Long.MAX_VALUE / 10) {
                if (num == 0) {
                    break;
                }
                extractMnt *= 10;
                exp--;
            }
        }
        mantissa = (long) extractMnt;
    }

    void negative() {
        sign = !sign;
    }

    FloatNumber add(FloatNumber num) {
        double addResultDouble = 0;
        FloatNumber addResult = new FloatNumber();
        addResultDouble = toDouble() + num.toDouble();
        addResult.fromDouble(addResultDouble);
        return addResult;
    }

    FloatNumber sub(FloatNumber num) {
        double subResultDouble = 0;
        FloatNumber subResult = new FloatNumber();
        subResultDouble = toDouble() - num.toDouble();
        subResult.fromDouble(subResultDouble);
        return subResult;
    }

    public static void main(String[] args) {
        FloatNumber test = new FloatNumber("-1010999.999999999999999977777777777773e2");
        FloatNumber threeParam = new FloatNumber(false, 1844674407370955160L, 18);
        threeParam.fromDouble(-3456.87e5);
        FloatNumber a = new FloatNumber(false, 125, 0);
        FloatNumber b = new FloatNumber(true, 25, 0);
        System.out.println(a.add(b));
        System.out.println(a.sub(b));
        System.out.println(Long.MAX_VALUE);

    }
}
