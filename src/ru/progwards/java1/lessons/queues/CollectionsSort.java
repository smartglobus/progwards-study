package ru.progwards.java1.lessons.queues;

import java.util.*;

public class CollectionsSort {

    // переделать алгоритм из класса ArraySort из ДЗ про массивы, на коллекции. Не использовать встроенные методы сортировок
    public static void mySort(Collection<Integer> data) {
        List<Integer> mySort = new ArrayList<>(data);

        for (int i = 0; i < data.size(); i++) {
            ListIterator<Integer> iterator = mySort.listIterator(i);
            Integer currMin = iterator.next();
            while (iterator.hasNext()) {
                Integer curr = iterator.next();
                if (currMin > curr) {
                    Collections.swap(mySort, mySort.indexOf(currMin), mySort.lastIndexOf(curr));
                    currMin = curr;
                }
            }
        }
        Collections.copy((List<Integer>) data, mySort);
    }//           85,73,43,60,73,66,7

    public static void minSort(Collection<Integer> data) {
        List<Integer> dataList = new ArrayList<>(data);
        List<Integer> minSort = new ArrayList<>();
        while (!dataList.isEmpty()) {
            minSort.add(Collections.min(dataList));
            dataList.remove(Collections.min(dataList));
        }
        Collections.copy((List<Integer>) data, minSort);
    }

    public static void collSort(Collection<Integer> data) {
        Collections.sort((List<Integer>) data);
    }

    // сравнить производительность методов и вернуть их имена, отсортированные в порядке производительности, первый - самый быстрый.
// В случае равенства производительности каких-то методов, возвращать их названия в алфавитном порядке.
    public static Collection<String> compareSort() {

        Set<SortTime> compareResult = new TreeSet<>();
//        new Comparator<SortTime>() {
//            @Override
//            public int compare(SortTime o1, SortTime o2) {
//                int result = Long.compare(o1.sortTime, o2.sortTime);
//                if (result == 0) {
//                    return o1.name.compareTo(o2.name);
//                }
//                return result;
//            }
//        });

        Queue<String> output = new ArrayDeque<>();

        // создание коллекции случайным образом перемешанных int от 0 до 10000
        List<Integer> randColl = new ArrayList<>(1000);
        for (int i = 0; i < 1000; i++) randColl.add(i);
        Collections.shuffle(randColl);

        long startTime = new Date().getTime();
        mySort(randColl);
        long mySortTime = new Date().getTime() - startTime;
        SortTime mySort = new SortTime("mySort", mySortTime);
        compareResult.add(mySort);
//        System.out.println("\n" + mySort.getName() + "  " + mySort.getSortTime());

        startTime = new Date().getTime();
        minSort(randColl);
        long minSortTime = new Date().getTime() - startTime;
        SortTime minSort = new SortTime("minSort", minSortTime);
        compareResult.add(minSort);
//        System.out.println("\n" + minSort.getName() + "  " + minSort.getSortTime());

        startTime = new Date().getTime();
        collSort(randColl);
        long collSortTime = new Date().getTime() - startTime;
        SortTime collSort = new SortTime("collSort", collSortTime);
        compareResult.add(collSort);
//        System.out.println("\n" + collSort.getName() + "  " + collSort.getSortTime());

        for (SortTime st : compareResult) {
            output.offer(st.getName());
        }

        return output;
    }

    static class SortTime implements Comparable<SortTime> {
        String name;
        long sortTime;

        public SortTime(String name, long sortTime) {
            this.name = name;
            this.sortTime = sortTime;
        }

        @Override
        public int compareTo(SortTime o1) {
            int result = Long.compare(sortTime, o1.sortTime);
            if (result == 0) {
                return name.compareTo(o1.name);
            }
            return result;
        }

        public String getName() {
            return name;
        }

//        public long getSortTime() {
//            return sortTime;
//        }
    }

    public static void main(String[] args) {
        List<Integer> test = new ArrayList<>(Arrays.asList(85,73,43,60,73,66,7));
        mySort(test);
        for (Integer t : test) System.out.print(t + " ");
        System.out.println();
        for (String s : compareSort()) System.out.println(s);
    }
}
/*
Вопросы:
1. Какой лучше тип коллекции String выбрать здесь для output
2. Где правильнее писать компаратор, метод compareTo в классе, при объявлении TreeSet или вообще отдельным методом?
3.
 */