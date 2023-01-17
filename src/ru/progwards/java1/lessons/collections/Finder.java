package ru.progwards.java1.lessons.collections;

import java.util.*;

public class Finder {

    // найти 2 соседних числа в коллекции сумма которых минимальна, вернуть коллекцию, содержащую индексы этих чисел
    public static Collection<Integer> findMinSumPair(Collection<Integer> numbers) {
        List<Integer> nums = new ArrayList<>(numbers);
        List<Integer> twoInd = new ArrayList<>();

        Iterator<Integer> iterFromZero = nums.iterator();
        ListIterator<Integer> iterFromOne = nums.listIterator(1);
        int numCurrent = iterFromZero.next();
        int numNxtCurrent = iterFromOne.next();
        int sumResult = numCurrent + numNxtCurrent;
        int num = numCurrent;
        int numNxt = numNxtCurrent;

        while (iterFromOne.hasNext()) {
            numCurrent = iterFromZero.next();
            numNxtCurrent = iterFromOne.next();

            if ((numCurrent + numNxtCurrent) < sumResult) {
                sumResult = numCurrent + numNxtCurrent;
                num = numCurrent;
                numNxt = numNxtCurrent;
            }
        }
        twoInd.add(0, num);
        twoInd.add(1, numNxt);

        return twoInd;
    }

    // найти локальные максимумы - числа, которые больше соседа справа и слева.
    // Первый и последний элемент коллекции не может являться локальным  максимумом, вернуть коллекцию, содержащую значения этих максимумов
    public static Collection<Integer> findLocalMax(Collection<Integer> numbers) {
        List<Integer> nums = new ArrayList<>(numbers);
        List<Integer> lokalMaxRecord = new ArrayList<>();
        ListIterator<Integer> iterator = nums.listIterator(1);
        int last = iterator.next();

        while (iterator.hasNext()) {
            int current = iterator.next();
            if (last > current) {
                lokalMaxRecord.add(last);
            }
            last = current;
        }
        return lokalMaxRecord;
    }

    public static void main(String[] args) {

        List<Integer> nt = new ArrayList<>(Arrays.asList(-7, -4, 9, 54, -5, 9, 25, 24, 45, 8));

        for (Integer value : nt) System.out.print(value + " ");
        System.out.println("\n");
        for (Integer integer : findMinSumPair(nt)) System.out.print(integer + " ");
        System.out.println("\n");
        for (Integer integer : findLocalMax(nt)) System.out.print(integer + " ");
        System.out.println("\n");

    }
}
