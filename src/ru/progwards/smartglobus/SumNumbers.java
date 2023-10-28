package ru.progwards.smartglobus;

import java.util.ArrayList;
import java.util.List;
public class SumNumbers {
    // раскладывает параметр number, как всевозможные уникальные комбинации сумм натуральных чисел, например:
    // 5 = 4+1 = 3+2 = 3+1+1 = 2+2+1 = 2+1+1+1 = 1+1+1+1+1
    public static String asNumbersSum(int number) {
        return number + divideIntoTerms(number, 1, "");
    }

    public static String divideIntoTerms(int number, int secondTerm, String lastTerm) {
        if(number == 1)
            return "";
        if(number/2 < secondTerm)
            return divideIntoTerms(number-1, 1, lastTerm + "+1");
        return " = " + (number-secondTerm) + "+" + secondTerm + lastTerm + divideIntoTerms(number, secondTerm+1, lastTerm);

    }

    public static void main(String[] args) {
        System.out.println(asNumbersSum(8));
        System.out.println("5 = 4+1 = 3+2 = 3+1+1 = 2+2+1 = 2+1+1+1 = 1+1+1+1+1".equals(asNumbersSum(5)));
    }
}

//
 class NumberCombinations {
    public static List<List<Integer>> getAllCombinations(int number) {
        List<List<Integer>> result = new ArrayList<>();
        getAllCombinationsRecursive(number, new ArrayList<>(), result);
        return result;
    }

    private static void getAllCombinationsRecursive(int number, List<Integer> currentCombination, List<List<Integer>> result) {
        if (number == 0) {
            result.add(new ArrayList<>(currentCombination));
            return;
        }

        int maxNum = Math.min(number, currentCombination.isEmpty() ? number : currentCombination.get(currentCombination.size() - 1));
        for (int i = 1; i <= maxNum; i++) {
            currentCombination.add(i);
            getAllCombinationsRecursive(number - i, currentCombination, result);
            currentCombination.remove(currentCombination.size() - 1);
        }
    }

    public static void main(String[] args) {
        int number = 5;
        List<List<Integer>> combinations = getAllCombinations(number);
        for (List<Integer> combination : combinations) {
            System.out.println(combination);
        }
    }
}
//В методе printCombinationsHelper, мы используем рекурсию для перебора всех возможных комбинаций.
// Передаем число number и текущую комбинацию combination в каждом рекурсивном вызове.
// Переменная max используется для ограничения следующих чисел, которые могут быть добавлены к комбинации, чтобы избежать повторений.
//
//В методе printCombinations, мы вызываем вспомогательный метод printCombinationsHelper и передаем начальное значение для number и пустую комбинацию.
 class NumberCombinationsChatGPT {
    public static void printCombinations(int number) {
        printCombinationsHelper(number, "", number);
    }

    private static void printCombinationsHelper(int number, String combination, int max) {
        if (number == 0) {
            System.out.println(combination);
            return;
        }

        for (int i = 1; i <= Math.min(max, number); i++) {
            if (combination.isEmpty()) printCombinationsHelper(number - i, combination + i, i);
            else printCombinationsHelper(number - i, combination + "+" + i, i);
        }
    }

    public static void main(String[] args) {
        int number = 6;
        System.out.println("Number combinations for " + number + ":");
        printCombinations(number);
    }
}
