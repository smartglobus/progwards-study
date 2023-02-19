package ru.progwards.java1.lessons.maps;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class FiboMapCache {
    private Map<Integer, BigDecimal> fiboCache = new HashMap<>();
    boolean cacheOn;

    public FiboMapCache(boolean cacheOn) {
        this.cacheOn = cacheOn;
    }

    //в функции проверить, находится ли вычисленное значение для n в кэше, и если да - вернуть его из кэша, если нет - рассчитать и добавить в кэш.
// Учитывать значение переменной cacheOn
    public BigDecimal fiboNumber(int n) {
        if (cacheOn && fiboCache.containsKey(n)) return fiboCache.get(n);
        return fiboCache.put(n, fibonacci(n));
    }

    public static BigDecimal fibonacci(int n) {
        if (n == 1) return BigDecimal.ONE;

        BigDecimal F = BigDecimal.ZERO;
        BigDecimal lastF = BigDecimal.ONE;
        BigDecimal penultF = BigDecimal.ZERO;

        for (int i = 1; i < n; i++) {
            F = penultF.add(lastF);
            penultF = lastF;
            lastF = F;
        }
        return F;
    }

    public void clearCahe() { // clearCache()???
        fiboCache.clear();
    }

    //тест для расчета чисел Фибоначчи от n = 1 до 1000 включительно и замерить разницу во времени с on = true и on = false,
// результат вывести на экран в формате "fiboNumber cacheOn=??? время выполнения ???" для cacheOn=true и cacheOn=false,
// вместо ??? вывести реальные значения в мсек.
    public static void test() {
        FiboMapCache fmcTest = new FiboMapCache(false);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            fmcTest.fiboNumber(i);
        }
        System.out.println("fiboNumber cacheOn=" + fmcTest.cacheOn + " время выполнения " + (System.currentTimeMillis() - startTime));

        fmcTest.cacheOn = true;

        startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            fmcTest.fiboNumber(i);
        }
        System.out.println("fiboNumber cacheOn=" + fmcTest.cacheOn + " время выполнения " + (System.currentTimeMillis() - startTime));
    }

    public static void main(String[] args) {
        FiboMapCache.test();
    }
}
