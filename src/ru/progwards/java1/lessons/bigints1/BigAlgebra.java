package ru.progwards.java1.lessons.bigints1;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BigAlgebra {
    public static BigDecimal fastPow(BigDecimal num, int pow) {
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


    public static BigInteger fibonacci(int n) {
        BigInteger F = BigInteger.ZERO;
        if (n == 1) {
            return BigInteger.ONE;
        }

        BigInteger lastF = BigInteger.ONE;
        BigInteger preLastF = BigInteger.ZERO;

        for (int i = 1; i < n; i++) {
            F = preLastF.add(lastF);
            preLastF = lastF;
            lastF = F;
        }
        return F;
    }


    public static void main(String[] args) {
        System.out.println(new BigAlgebra().fastPow(new BigDecimal("-10"), 5));
        System.out.println(new BigAlgebra().fibonacci(12));
    }
}
