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
        double dNum = Double.valueOf(num);
        if (dNum >= 0) {
            sign = true;
        } else {
            //меняем знак, значение sing остаётся false по умолчанию
            dNum = 0 - dNum;
        }

        this.exp = 0;
        // блок для чисел >= 1
        if (dNum >= 1) {
            double extractMnt = dNum;
// если dNum больше Long.MAX_VALUE , удаляем младшие разряды,
// деля на 10 и корректируя exp, пока мантисса не заполнит по длине ячейку типа long
                while (Math.floor(extractMnt) > Long.MAX_VALUE) {
                    extractMnt /= 10;
                    exp++;
                }
// если dNum меньше Long.MAX_VALUE, умножать на 10, корректируя exp, пока мантисса не заполнит по длине ячейку типа long
            while (extractMnt < Long.MAX_VALUE/10){
                extractMnt *= 10;
                exp--;
            }

            this.mantissa = (long) extractMnt;
        }
        // блок для чисел от 0 до 1
        if (dNum < 1) {
            double extractMnt = dNum;

            // умножать на 10, пока мантисса не заполнит по длине ячейку типа long
            do {
                extractMnt *= 10;
                exp--;
            }
            while ((long)extractMnt <= Long.MAX_VALUE/10);
            this.mantissa = (long) extractMnt;
        }
    }

    public static void main(String[] args) {
        FloatNumber test = new FloatNumber("0.999999999999999");
        System.out.println(test);
        System.out.println(Long.MAX_VALUE);
        System.out.println((long)9223372036854775807.567);
    }

    @Override
    public String toString() {
        return "FloatNumber{" +
                "sign=" + sign +
                ", mantissa=" + mantissa +
                ", exp=" + exp +
                '}';
    }
}
