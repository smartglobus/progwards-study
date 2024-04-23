package ru.progwards.java2.lessons.sort;

import java.io.*;
import java.util.*;

public class ExternalSort {
    private static final long SUB_ARRAY_STEP = 20000 * 12;

    static class SubMin {
        int value;
        long pointer;
        long nextSubPointer;

        public SubMin(int value, long position) {
            this.value = value;
            this.pointer = position;
            nextSubPointer = position + SUB_ARRAY_STEP;
        }
    }

    private static SubMin getMin(List<SubMin> mins) {
        SubMin min = mins.get(0);
        for (int i = 1; i < mins.size(); i++) {
            SubMin curr = mins.get(i);
            if (curr.value < min.value) min = curr;
        }

        return min;
    }

    static void sort(String inFileName, String outFileName) {
//        File sortedBy20000 = sort_by_20000(sort_by_10000(inFileName));
        List<SubMin> mins = new ArrayList<>(10000);

        File sortedBy20000 = new File("sort_by_20000.txt");// test

        try (RandomAccessFile raf = new RandomAccessFile(sortedBy20000, "r");
             FileWriter writer = new FileWriter(outFileName)) {
            for (int i = 0; i < 10000; i++) {
                long pos = i * SUB_ARRAY_STEP;
                raf.seek(pos);
                mins.add(new SubMin(Integer.parseInt(raf.readLine()), pos));
            }

            while (!mins.isEmpty()) {
                SubMin min = getMin(mins);
                writer.write(min.value + "\n");
                min.pointer += 12;
                if (min.pointer == min.nextSubPointer) mins.remove(min);
                else {
                    raf.seek(min.pointer);
                    min.value = Integer.parseInt(raf.readLine());
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        Deque<Integer> ints = new ArrayDeque<>(10000);

        try (Scanner scanner = new Scanner(new FileReader(fileName));
             FileWriter writer = new FileWriter(result)) {
            for (int i = 0; i < 10_000; i++) {

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
        System.out.println("Go!");
        long start = System.currentTimeMillis();
        sort("data.txt", "sortirovannyi_file.txt");
//        sort_by_10000("data.txt");
//        sort_by_20000(new File("sort_by_10000.txt"));
        System.out.println("all sorted, secs: " + ((System.currentTimeMillis() - start)) / 1000);// all sorted, secs: 61753
    }
}
