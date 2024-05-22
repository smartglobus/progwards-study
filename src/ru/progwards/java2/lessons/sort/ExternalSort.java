package ru.progwards.java2.lessons.sort;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ExternalSort {
    private final static String TMP_DIR_10000files = "C://sorting_tmp";
    private final static String TMP_DIR_100files = "C://sorting_tmp_bigFiles";

    static class SubMin implements Comparable<SubMin> {
        private int value; // текущее минимальное значение подмассива
        private BufferedReader reader;

        private SubMin(int value, BufferedReader reader) {
            this.value = value;
            this.reader = reader;
        }

        @Override
        public int compareTo(SubMin o) {
            return Integer.compare(this.value, o.value);
        }
    }

    static void sort(String inFileName, String outFileName) {
        long start = System.currentTimeMillis();
        File sortedBy10000 = sort_by_10000(inFileName); // один файл с 20_000 сортированными подмассивами по 10_000 чисел
        System.out.println("sorted by 10000, time from start, s: " + ((System.currentTimeMillis() - start)) / 1000);
        sort_to_10000files(sortedBy10000); // 10_000 файлов, с сортированными 20_000 числами в каждом
        System.out.println("sorted by 20000, time from start, s: " + ((System.currentTimeMillis() - start)) / 1000);
        mergeFiles(); // объединяет 10_000 файлов в 100 файлов
        System.out.println("100 files by 20_000_000 sorted, time from start, s: " + ((System.currentTimeMillis() - start)) / 1000);
        mergeFiles(outFileName); // объединяет 100 файлов в один результирующий файл
        System.out.println("all sorted, time from start, s: " + ((System.currentTimeMillis() - start)) / 1000);
    }


    // сортировка подмассивами по 10000 значений, с записью в один новый файл
    private static File sort_by_10000(String fileName) {
        File result = new File("sort_by_10000.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName));
             FileWriter writer = new FileWriter(result);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            for (int i = 0; i < 20_000; i++) {
                int[] ints = new int[10_000];
                for (int j = 0; j < 10_000; j++) {
                    ints[j] = Integer.parseInt(reader.readLine());
                }
                QuickSort.quickSort(ints);
                for (int n : ints) {
                    bufferedWriter.write(n + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    // сортировка на 10000 подмассивов, с записью в отдельные файлы по 20000 значений
    private static void sort_to_10000files(File fileName) {
        Deque<Integer> ints = new ArrayDeque<>(10000);

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            Path tempFiles = Path.of(TMP_DIR_10000files);
            if (Files.notExists(tempFiles)) Files.createDirectory(tempFiles);

            for (int i = 1; i <= 10_000; i++) {
                Path file = Path.of(TMP_DIR_10000files, "subArr_" + i);
                if (Files.notExists(file)) Files.createFile(file);
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file.toString()))) {

                    for (int j = 0; j < 10_000; j++)
                        ints.addLast(Integer.parseInt(reader.readLine()));

                    int fromFile = Integer.parseInt(reader.readLine());
                    int fileCount = 0;
                    do {
                        if (ints.isEmpty() || fileCount < 10000 && fromFile <= ints.peekFirst()) {
                            writer.write(fromFile + "\n");
                            fileCount++;
                            if (fileCount < 10000) {
                                fromFile = Integer.parseInt(reader.readLine());
                            }
                        } else {
                            writer.write(ints.pollFirst() + "\n");
                        }
                    } while (!ints.isEmpty() || fileCount < 10000);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileName.delete();
    }

    // merging 10000 files to 100 files
    private static void mergeFiles() {
        Path tempFiles = Path.of(TMP_DIR_100files);

        try {
            if (Files.notExists(tempFiles)) Files.createDirectory(tempFiles);
            for (int j = 0; j < 100; j++) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(TMP_DIR_100files + "/subArrBig_" + (j + 1)));
                PriorityQueue<SubMin> miins = new PriorityQueue<>(100);
                for (int i = 1; i <= 100; i++) {
                    BufferedReader reader = new BufferedReader(new FileReader(TMP_DIR_10000files + "/subArr_" + ((j * 100) + i)));
                    miins.offer(new SubMin(Integer.parseInt(reader.readLine()), reader));
                }

                while (!miins.isEmpty()) {
                    SubMin miin = miins.poll();
                    writer.write(miin.value + "\n");
                    String nextLine = "";
                    if ((nextLine = miin.reader.readLine()) != null) {
                        int n = Integer.parseInt(nextLine);
                        miins.offer(new SubMin(n, miin.reader));
                    } else {
                        miin.reader.close();
                    }
                }
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            for (int i = 1; i <= 10000; i++) {
                Files.delete(Path.of(TMP_DIR_10000files + "/subArr_" + i));
            }
            Files.delete(Path.of(TMP_DIR_10000files));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // merging 100 files to the output file
    private static void mergeFiles(String outFileName) {
        Path out = Path.of(outFileName);

        try {
            if (Files.notExists(out)) Files.createFile(out);
            BufferedWriter writer = new BufferedWriter(new FileWriter(outFileName));

            PriorityQueue<SubMin> mins = new PriorityQueue<>(100);
            for (int i = 1; i <= 100; i++) {
                BufferedReader reader = new BufferedReader(new FileReader(TMP_DIR_100files + "/subArrBig_" + i));
                mins.offer(new SubMin(Integer.parseInt(reader.readLine()), reader));
            }

            while (!mins.isEmpty()) {
                SubMin miin = mins.poll();
                writer.write(miin.value + "\n");
                String nextLine = "";
                if ((nextLine = miin.reader.readLine()) != null) {
                    int n = Integer.parseInt(nextLine);
                    mins.offer(new SubMin(n, miin.reader));
                } else {
                    miin.reader.close();
                }
            }
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            for (int i = 1; i <= 100; i++) {
                Files.delete(Path.of(TMP_DIR_100files + "/subArrBig_" + i));
            }
            Files.delete(Path.of(TMP_DIR_100files));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        sort("data.txt", "sorted.txt");
    }
}
