package ru.progwards.java2.lessons.trees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

public class TestAVLTreeVsTreeMap {
    private AvlTree<Integer, Integer> avlTree = new AvlTree<>();
    private TreeMap<Integer, Integer> treeMap = new TreeMap<>();
    private List<Integer> data = new ArrayList<>();
    private List<Integer> shuffledData;
    long start;

    public TestAVLTreeVsTreeMap() {
        for (int i = 0; i < 2400000; i++) data.add(i);
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
    }
}
