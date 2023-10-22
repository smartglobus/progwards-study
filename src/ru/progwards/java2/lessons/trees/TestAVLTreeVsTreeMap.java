package ru.progwards.java2.lessons.trees;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TestAVLTreeVsTreeMap {
    private AvlTree<Integer, Integer> avlTree = new AvlTree<>();
    private TreeMap<Integer, Integer> treeMap = new TreeMap<>();
    private List<Integer> data = new ArrayList<>();
    private List<Integer> shuffledData;

    private AvlTree<String, Integer> avlTokensTree = new AvlTree<>();
    private TreeMap<String, Integer> treeMapTokens = new TreeMap<>();
    private Set<String> stringSet = new TreeSet<>();
    private ArrayList<String> tokensList = new ArrayList<>();

    private long start;

    public TestAVLTreeVsTreeMap() {
        for (int i = 0; i < 500000; i++) data.add(i);
        shuffledData = new ArrayList<>(data);
        Collections.shuffle(shuffledData);
    }

    void putSorted() {
        start = System.currentTimeMillis();
        for (int i : data) avlTree.put(i, i);
        System.out.println("AVL put time = " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (int i : data) treeMap.put(i, i);
        System.out.println("TreeMap put time = " + (System.currentTimeMillis() - start));
    }

    void findSorted() {
        start = System.currentTimeMillis();
        for (int i : data) avlTree.find(i);
        System.out.println("AVL find time = " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (int i : data) treeMap.get(i);
        System.out.println("TreeMap find time = " + (System.currentTimeMillis() - start));
    }

    void deleteSorted() {
        start = System.currentTimeMillis();
        for (int i : data) avlTree.delete(i);
        System.out.println("AVL delete time = " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (int i : data) treeMap.remove(i);
        System.out.println("TreeMap delete time = " + (System.currentTimeMillis() - start));
    }


    void putRandom() {
        start = System.currentTimeMillis();
        for (int i : shuffledData) avlTree.put(i, i);
        System.out.println("AVL put time = " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (int i : shuffledData) treeMap.put(i, i);
        System.out.println("TreeMap put time = " + (System.currentTimeMillis() - start));
    }

    void findRandom() {
        start = System.currentTimeMillis();
        for (int i : shuffledData) avlTree.find(i);
        System.out.println("AVL find time = " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (int i : shuffledData) treeMap.get(i);
        System.out.println("TreeMap find time = " + (System.currentTimeMillis() - start));
    }

    void deleteRandom() {
        start = System.currentTimeMillis();
        for (int i : shuffledData) avlTree.delete(i);
        System.out.println("AVL delete time = " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (int i : shuffledData) treeMap.remove(i);
        System.out.println("TreeMap delete time = " + (System.currentTimeMillis() - start));
    }


    void initTokens(String file) {
        try (FileReader reader = new FileReader(file); Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tokens = line.split("[\\p{Punct} ]");
                for (int i = 0; i < tokens.length; i++) if (!"".equals(tokens[i])) stringSet.add(tokens[i]);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        tokensList.addAll(stringSet);
    }

    void putTokens() {
        start = System.currentTimeMillis();
        for (int i = 0; i < tokensList.size(); i++) avlTokensTree.put(tokensList.get(i), i);
        System.out.println("AVL put tokens time = " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < tokensList.size(); i++) treeMapTokens.put(tokensList.get(i), i);
        System.out.println("TreeMap put tokens time = " + (System.currentTimeMillis() - start));
    }

    void findTokens() {
        start = System.currentTimeMillis();
        for (String i : tokensList) avlTokensTree.find(i);
        System.out.println("AVL find tokens time = " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (String i : tokensList) treeMapTokens.get(i);
        System.out.println("TreeMap find tokens time = " + (System.currentTimeMillis() - start));
    }

    void deleteTokens() {
        start = System.currentTimeMillis();
        for (String i : tokensList) avlTokensTree.delete(i);
        System.out.println("AVL delete tokens time = " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (String i : tokensList) treeMapTokens.remove(i);
        System.out.println("TreeMap delete tokens time = " + (System.currentTimeMillis() - start));
    }

    public static void main(String[] args) {
        TestAVLTreeVsTreeMap test = new TestAVLTreeVsTreeMap();
        System.out.println("Sorted data test:");
        test.putSorted();
        test.findSorted();
        test.deleteSorted();

        System.out.println("\nRandom data test:");
        test.putRandom();
        test.findRandom();
        test.deleteRandom();

        System.out.println("\nTokens test:");
        test.initTokens("C:\\Users\\User\\Documents\\Progwards\\Материалы курса\\Продвинутый курс\\F8 Дополнительные материалы к занятию-20230930\\wiki.train.tokens");
        test.putTokens();
        test.findTokens();
        test.deleteTokens();
    }
}
