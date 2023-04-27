package ru.progwards.java2.lessons.recursion;

import java.util.*;
import java.util.stream.Collectors;

// Реализовать класс, AsNumbersSum, содержащий метод public static String asNumbersSum(int number), который раскладывает параметр number
// как всевозможные уникальные комбинации сумм натуральных чисел, например:
//    5 = 4+1 = 3+2 = 3+1+1 = 2+2+1 = 2+1+1+1 = 1+1+1+1+1
// Строка должна содержать результат, отформатированный точно, как указано в примере.
// Повторные комбинации не допускаются, например, если а строке уже есть 3+2, то 2+3 там быть не должно.
// Задача должна быть решена методом рекурсии, циклы использовать запрещено.
public class AsNumbersSum {
    private static List<Integer> list = new ArrayList<>();
    private static int num;
    private static String str = "";

    public static String asNumbersSum(int number) {
        if (number < 2) return String.valueOf(number);
        if (number == 2) return "2 = 1+1";
        num = number;
        list.add(0, number - 1);
        list.add(1, 1);
        str += number + " = " + (number - 1) + "+" + 1 + " = " + func();
        return str.substring(0, str.length() - 2);// удаление последнего знака " ="
    }

    private static String func() {
        // если предпоследнее слагаемое единица (и все, которые правее, тоже единицы),
        // начальный член уменьшается на 1 и весь массив очищается и заполняется методом fillList
        if (list.get(1) == 1) {
            int first = list.get(0) - 1;
            list.clear();
            fillList(first, num);
            // добавление опорного варианта после fillList
            str += list.stream().map(String::valueOf).collect(Collectors.joining("+")) + " = ";
            if (list.get(0) == 1) return str;// выход из рекурсии, когда все слагаемые единицы
        }
        // нахождение крайнего справа элемента больше 1
        int n = list.stream().sorted().dropWhile(el -> el < 2).min(Comparator.comparingInt(el -> el)).get();
        int i = list.lastIndexOf(n);
// уменьшение i-го числа на единицу с добавлением единицы к следующему числу. Если i-ое число 2, то добавление 1 в конец суммы
        if (list.get(i) > 2 && i < list.size() - 1) list.set(i + 1, list.get(i + 1) + 1);
        else list.add(1);
        list.set(i, list.get(i) - 1);

        str += list.stream().map(String::valueOf).collect(Collectors.joining("+")) + " = ";
        return func();
    }

    // наполнение списка x повторами числа first, плюс остаток: first+first+first+...+(num-first*x)
    private static void fillList(int n, int numExc) {
        list.add(n);
        numExc -= n;
        if (numExc - n > 0) {
            fillList(n, numExc);
        } else {
            list.add(numExc);
        }
    }

    public static void main(String[] args) {
        System.out.println(asNumbersSum(7));
    }
}