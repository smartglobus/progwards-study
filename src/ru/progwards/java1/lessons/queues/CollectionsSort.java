package ru.progwards.java1.lessons.queues;

import java.util.*;

public class CollectionsSort {

    // переделать алгоритм из класса ArraySort из ДЗ про массивы, на коллекции. Не использовать встроенные методы сортировок
    public static void mySort(Collection<Integer> data) {
        ArrayList<Integer> mySort = new ArrayList<>(data);

        for (int i = 0; i < data.size(); i++) {
            ListIterator<Integer> iterator = mySort.listIterator(i);
            Integer prev = iterator.next();
            while (iterator.hasNext()) {
                Integer curr = iterator.next();
                if (prev > curr) Collections.swap(mySort, mySort.indexOf(prev), mySort.indexOf(curr));
                prev = curr;
            }
        }
        Collections.copy((ArrayList<Integer>) data, mySort);
    }

    public static void minSort(Collection<Integer> data) {
        ArrayList<Integer> dataList = new ArrayList<>(data);
        ArrayList<Integer> minSort = new ArrayList<>();
       while (!dataList.isEmpty()){
            minSort.add(Collections.min(dataList));
            dataList.remove(Collections.min(dataList));
        }
        Collections.copy((ArrayList<Integer>)data, minSort);
    }


    public static void main(String[] args) {
        List<Integer> test = new ArrayList<>(Arrays.asList(11, 2, 3, 48, 5));
        minSort(test);
        for (Integer t : test) System.out.print(t + " ");
    }
}
