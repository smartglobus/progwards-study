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

    // проверить, содержит ли коллекция все числа от 1 до size(), порядок может быть произвольный
    public static boolean findSequence(Collection<Integer> numbers) {
        Collection<Integer> numsToSize = new ArrayList<>();
        for (int i = 1; i <= numbers.size(); i++)
            numsToSize.add(i);
        return numbers.containsAll(numsToSize);
    }

    // найдите максимальное количество повторяющихся подряд элементов.
    // Результат вернуть в виде строки <элемент>:<количество>, например Василий:5.
    // При равенстве максимального количества у разных повторяющихся элементов,
    // вернуть результат для элемента, повторяющаяся последовательность которого началась с наименьшего индекса.
    public static String findSimilar(Collection<String> names) {
        Iterator<String> nameList = names.iterator();
        String maxRepName = nameList.next();
        int repMax = 0;
        int currentRep = 1;
        String currentName = nameList.next();
        String previousName;
        while (nameList.hasNext()) {
            previousName = currentName;
            currentName = nameList.next();
            if (currentName.equals(previousName)) {
                currentRep++;
                if (currentRep > repMax) {
                    maxRepName = currentName;
                    repMax = currentRep;
                }
            } else {
                currentRep = 1;
            }
        }
        return maxRepName + ":" + repMax;
    }

    public static void main(String[] args) {

        List<Integer> nt = new ArrayList<>(Arrays.asList(-7, -4, 9, 54, -5, 9, 25, 24, 45, 8));
        List<Integer> oneTwoThree = new ArrayList<>(Arrays.asList(1, 3, 2, 5, 4));

        for (Integer value : nt) System.out.print(value + " ");
        System.out.println("\n");
        for (Integer integer : findMinSumPair(nt)) System.out.print(integer + " ");
        System.out.println("\n");
        for (Integer integer : findLocalMax(nt)) System.out.print(integer + " ");
        System.out.println("\n");
        System.out.println(findSequence(nt));
        System.out.println(findSequence(oneTwoThree));
        System.out.println("\n");
        List<String> names = new ArrayList<>(Arrays.asList("Fedor", "Masha", "Masha", "Petya", "Petya", "Masha", "Masha", "Masha", "Vasya", "Petya", "Masha", "Masha", "Masha", "Masha", "Vasya", "Vasya", "Vasya", "Vasya", "Petya"));
        System.out.println(findSimilar(names) + "\n");

    }
}
