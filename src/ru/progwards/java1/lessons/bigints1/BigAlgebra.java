package ru.progwards.java1.lessons.bigints1;

import java.math.BigDecimal;

public class BigAlgebra {
    BigDecimal fastPow(BigDecimal num, int pow) {
        BigDecimal result = BigDecimal.ONE;
        BigDecimal z = num;

        for (int i = 1; i < pow; i <<= 1) {
            if ((pow & i) != 0) {
                result = result.multiply(z);
            }
            z = z.multiply(z);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(new BigAlgebra().fastPow(new BigDecimal("10"), 7));
    }
}
