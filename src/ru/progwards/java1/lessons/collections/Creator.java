package ru.progwards.java1.lessons.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Creator {
    public static Collection<Integer> fillEven(int n) {
        Collection<Integer> list = new ArrayList<>();
        int lim = n * 2;
        for (int i = 2; i <= lim; i += 2)
            list.add(i);
        return list;
    }

    public static Collection<Integer> fillOdd(int n) {
        List<Integer> list = new ArrayList<>();
        int lim = n * 2;
        for (int i = 1; i < lim; i += 2)
            list.add(0, i);
        return list;
    }

    public static Collection<Integer> fill3(int n) {
        Collection<Integer> list = new ArrayList<>();
        int lim = n * 3;
        for (int i = 0; i < lim; i += 3) {
            list.add(i);
            list.add(i * i);
            list.add(i * i * i);
        }
        return list;
    }

    public static void main(String[] args) {

        for (Integer i : fillEven(6))
            System.out.println(i);
        System.out.println("");
        for (Integer i : fillOdd(6))
            System.out.println(i);
        System.out.println("");
        for (Integer i : fill3(3))
            System.out.println(i);
    }
}
//создать коллекцию и заполнить последовательностью четных возрастающих чисел начиная с 2, количество элементов в коллекции n
//создать коллекцию и заполнить последовательностью нечетных убывающих чисел, минимальное число в коллекции 1, количество элементов в коллекции n
//создать коллекцию и заполнить ее тройками чисел. Каждая тройка создается по алгоритму:
// первое число тройки - индекс числа в коллекции,
// второе - тот же индекс в квадрате,
// третье - тот же индекс в кубе,
// количество элементов в коллекции n*3