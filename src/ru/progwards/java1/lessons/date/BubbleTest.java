
package ru.progwards.java1.lessons.date;

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

        long sortTime = 0;
        long innerCycleStartTime = 0;

        long startSortIteration = 0;
        long startSortIteration10 = 0;
        long startSortIteration30 = 0;
        long startSortIteration50 = 0;
        long startSortIteration70 = 0;
        long startSortIteration90 = 0;

        long innerCycleSumTime = 0;
        long beforeIf = 0;
        long startShift = 0;

        long shiftTime = 0;
        long shiftCount = 0;
        long ifSumTime = 0;

        System.out.println("Выборочная оценка времени сортировки на разных фазах выполнения (для COUNT=100000):");

        for (int i = 0; i < a.length; i++) {
            if (i == 0) {
                startSortIteration = System.currentTimeMillis();
            }
            if (i == 10000) {
                startSortIteration10 = System.currentTimeMillis();
            }
            if (i == 30000) {
                startSortIteration30 = System.currentTimeMillis();
            }
            if (i == 50000) {
                startSortIteration50 = System.currentTimeMillis();
            }
            if (i == 70000) {
                startSortIteration70 = System.currentTimeMillis();
            }
            if (i == 90000) {
                startSortIteration90 = System.currentTimeMillis();
            }// сделать для каждой декады

            innerCycleStartTime = System.currentTimeMillis();
            for (int j = 0; j < a.length - i - 1; j++) {


                int n = j + 1;
                beforeIf = System.currentTimeMillis();
                if (a[j].compareTo(a[n]) < 0) {
// замерить суммарное время перестановки членов местами, со счётчиком таких перестановок
                    startShift = System.currentTimeMillis();
                    Integer tmp = a[j];
                    a[j] = a[n];
                    a[j] = tmp;

                    shiftTime += System.currentTimeMillis() - startShift;
                    ifSumTime += System.currentTimeMillis() - beforeIf;
                    shiftCount++;
                }

            }
            innerCycleSumTime += System.currentTimeMillis() - innerCycleStartTime;

            if (i == 10000) {
                System.out.println("Время сортировки итераций     0-10000   " + (System.currentTimeMillis() - startSortIteration)+ " мс   ");
            }
            if (i == 20000) {
                System.out.println("Время сортировки итераций 10000-20000   " + (System.currentTimeMillis() - startSortIteration10)+  " мс   ");
            }
            if (i == 40000) {
                System.out.println("Время сортировки итераций 30000-40000   " + (System.currentTimeMillis() - startSortIteration30)+ " мс   ");
            }
            if (i == 60000) {
                System.out.println("Время сортировки итераций 50000-60000   " + (System.currentTimeMillis() - startSortIteration50)+ " мс   ");
            }
            if (i == 80000) {
                System.out.println("Время сортировки итераций 70000-80000   " + (System.currentTimeMillis() - startSortIteration70)+ " мс   ");
            }
            if (i == 100000 - 1) {
                System.out.println("Время сортировки итераций 90000-100000   " + (System.currentTimeMillis() - startSortIteration90)+ " мс   ");
            }
        }
        sortTime = System.currentTimeMillis() - startTime;


        System.out.println("Вся сортировка                                                            " + sortTime + " мс   100%");
        System.out.println("Время работы внутреннего цикла (исх. строки  23-30)                       " + innerCycleSumTime + " мс   " + innerCycleSumTime * 100 / sortTime + "%");
        System.out.println("Суммарное время выполнения блока перестановки (исх. строки  25-29)         " + ifSumTime + " мс   " + ifSumTime * 100 / sortTime + "%");
        System.out.println("Суммарное время непосредственно операций перестановки (исх. строки  26-28) " + shiftTime + " мс   " + shiftTime * 100 / sortTime + "%");
        System.out.println("Количество произведённых перестановок  " + shiftCount + " раз");

    }


    public static void main(String[] args) {
        Integer[] a = new Integer[COUNT];
        fill(a);
        bubbleSort(a);
    }

}
