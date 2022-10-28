
package ru.progwards.java1.lessons.date;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class BubbleTest {
    public static final int COUNT = 100000;

    public static void fill(Integer[] a) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < COUNT; i++) {
            int n;
            do {
                n = ThreadLocalRandom.current().nextInt();
            } while (map.containsKey(n));
            a[i] = n;
            map.put(n, n);
        }
    }

    public static void bubbleSort(Integer[] a) {
        long startTime = System.currentTimeMillis();
        long outCycleTime = 0;
        long sortTime = 0;
        long outCycleStartTime = 0;
        long startSortIteration = 0;
        long stopSortIteration = 0;
        long outCycleEndTime = 0;

        for (int i = 0; i < a.length; i++) {
            if (i==90000){startSortIteration = System.currentTimeMillis();}

//            outCycleStartTime = System.currentTimeMillis();
            for (int j = 0; j < a.length - i - 1; j++) {// 1/70 ms ?????
//              startSortIteration = System.currentTimeMillis();
                int n = j + 1; // 12 - 24 ms за 100000-2 циклов

                if (a[j].compareTo(a[n]) < 0) {// 12-20 ms за 100000-2 циклов

                    Integer tmp = a[j];
                    a[j] = a[n];
                    a[j] = tmp;
                }


            }
            if (i==100000-1){System.out.println(System.currentTimeMillis() - startSortIteration);}

        }
        long endTime = System.currentTimeMillis();
//        System.out.println("outCycleStartTime  " + outCycleStartTime);
//        System.out.println("startSortIteration  " + startSortIteration);
        System.out.println("Вся сортировка  " + (endTime - startTime));
//        System.out.println("Внешний цикл в сумме = " + outCycleTime);
//        System.out.println("Собственно сортировка = " + sortTime);
    }

    // bubbleSort целиком 57228 (57512, 57801, мс
    public static void main(String[] args) {
        Integer[] a = new Integer[COUNT];
        fill(a);
        bubbleSort(a);
    }

}
