package ru.progwards.java2.lessons.sort;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import java.util.Scanner;

public class ExternalSort {


    static void sort(String inFileName, String outFileName) {
        File sortedBy20000 = sort_by_20000(sort_by_10000(inFileName));

    }

    private static File sort_by_10000(String fileName) {
        File result = new File("sort_by_10000.txt");


        try (Scanner scanner = new Scanner(new FileReader(fileName));
             FileWriter writer = new FileWriter(result)) {
            for (int i = 0; i < 20_000; i++) {
                int[] ints = new int[10_000];
                for (int j = 0; j < 10_000; j++) {
                    ints[j] = Integer.parseInt(scanner.nextLine());
                }
                QuickSort.quickSort(ints);
                for (int n : ints) {
                    writer.write(n + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static File sort_by_20000(File fileName) {
        File result = new File("sort_by_20000.txt");

        try (Scanner scanner = new Scanner(new FileReader(fileName));
             FileWriter writer = new FileWriter(result)) {

            for (int i = 0; i < 10_000; i++) {
                Deque<Integer> ints = new ArrayDeque<>();
                for (int j = 0; j < 10_000; j++) ints.addLast(Integer.parseInt(scanner.nextLine()));

                int fromFile = Integer.parseInt(scanner.nextLine());
                int fileCount = 0;
                do {
                    if (ints.isEmpty() || fileCount < 10000 && fromFile <= ints.peekFirst()) {
                        writer.write(String.format("%011d\n", fromFile));
                        fileCount++;
                        if (fileCount < 10000) fromFile = Integer.parseInt(scanner.nextLine());
                    } else {
                        writer.write(String.format("%011d\n", ints.pollFirst()));
                    }
                } while (!ints.isEmpty() || fileCount < 10000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

//        sort_by_10000("data.txt");
        sort_by_20000(new File("sort_by_10000.txt"));
        System.out.println("all lines sorted by 20000, millis: " + (System.currentTimeMillis() - start));

    }
}
