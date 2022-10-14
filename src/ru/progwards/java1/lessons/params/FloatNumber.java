package ru.progwards.java1.lessons.params;

public class FloatNumber {
    boolean sign;
    long mantissa;
    int exp;

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
        int dotCount = 0;

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

    public static void main(String[] args) {
        FloatNumber test = new FloatNumber(".103456789876543234567898765");
        System.out.println(test);
    }

    @Override
    public String toString() {
        long mantissaFirstDigit = mantissa;
        long mantissaWithoutFirstDigit = 0;
        int expCorrToStd = 0;

        while (mantissaFirstDigit >= 10) {
            mantissaFirstDigit /= 10;
            expCorrToStd++;
        }
        long dosensTimesExpCorr = 0;
        if (mantissa >= 10) {
            dosensTimesExpCorr = 1;
            for (int i = 0; i < expCorrToStd; i++) {
                dosensTimesExpCorr *= 10;
            }
        }
        mantissaWithoutFirstDigit = mantissa - mantissaFirstDigit * dosensTimesExpCorr;

// первая цифра плюс знак
        if (!sign) {
            mantissaFirstDigit *= -1;
        }

// если 'E' отлична от нуля
        if (exp + expCorrToStd != 0) {
            return mantissaFirstDigit + "." + mantissaWithoutFirstDigit + "E" + (exp + expCorrToStd);
        }
        return mantissaFirstDigit + "." + mantissaWithoutFirstDigit;
    }
}
