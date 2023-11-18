package ru.progwards.java2.lessons.trees;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.file.Files.readString;

/**
 * Класс для сравнительного тестирования производительности двух структур хранения данных, {@link AvlTree} и {@link TreeMap}.
 * AvlTree использует алгоритм АВЛ-дерева, а входящий в стандартный пакет java.util TreeMap основан на красно-чёрном дереве.
 *
 * @see AvlTree
 * @see TreeMap
 */
public class TestAVLTreeVsTreeMap {
    private AvlTree<Integer, Integer> avlTree = new AvlTree<>();
    private TreeMap<Integer, Integer> treeMap = new TreeMap<>();
    private List<Integer> data = new ArrayList<>();
    private List<Integer> shuffledData;

    private AvlTree<String, Integer> avlTokensTree = new AvlTree<>();
    private TreeMap<String, Integer> treeMapTokens = new TreeMap<>();

    private ArrayList<String> tokensList = new ArrayList<>();

    private long start;

    /**
     * Конструктор инициализирует списки сортированных и несортированных элементов.
     * Несортированный список получается перемешиванием данных сортированного.
     *
     * @param elements размер тестового массива.
     */

    public TestAVLTreeVsTreeMap(int elements) {
        for (int i = 0; i < elements; i++) data.add(i);
        shuffledData = new ArrayList<>(data);
        Collections.shuffle(shuffledData);
    }

    /**
     * Тестирование скорости добавления сортированных элементов.
     */
    void putSorted() {
        start = System.currentTimeMillis();
        for (int i : data) avlTree.put(i, i);
        System.out.println("AVL put time = " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (int i : data) treeMap.put(i, i);
        System.out.println("TreeMap put time = " + (System.currentTimeMillis() - start));
    }

    /**
     * Тестирование скорости поиска сортированных элементов.
     */
    void findSorted() {
        start = System.currentTimeMillis();
        for (int i : data) avlTree.find(i);
        System.out.println("AVL find time = " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (int i : data) treeMap.get(i);
        System.out.println("TreeMap find time = " + (System.currentTimeMillis() - start));
    }

    /**
     * Тестирование скорости удаления сортированных элементов.
     */
    void deleteSorted() {
        start = System.currentTimeMillis();
        for (int i : data) avlTree.delete(i);
        System.out.println("AVL delete time = " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (int i : data) treeMap.remove(i);
        System.out.println("TreeMap delete time = " + (System.currentTimeMillis() - start));
    }

    /**
     * Тестирование скорости добавления несортированных элементов.
     */
    void putRandom() {
        start = System.currentTimeMillis();
        for (int i : shuffledData) avlTree.put(i, i);
        System.out.println("AVL put time = " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (int i : shuffledData) treeMap.put(i, i);
        System.out.println("TreeMap put time = " + (System.currentTimeMillis() - start));
    }

    /**
     * Тестирование скорости поиска несортированных элементов.
     */
    void findRandom() {
        start = System.currentTimeMillis();
        for (int i : shuffledData) avlTree.find(i);
        System.out.println("AVL find time = " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (int i : shuffledData) treeMap.get(i);
        System.out.println("TreeMap find time = " + (System.currentTimeMillis() - start));
    }

    /**
     * Тестирование скорости удаления несортированных элементов.
     */
    void deleteRandom() {
        start = System.currentTimeMillis();
        for (int i : shuffledData) avlTree.delete(i);
        System.out.println("AVL delete time = " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (int i : shuffledData) treeMap.remove(i);
        System.out.println("TreeMap delete time = " + (System.currentTimeMillis() - start));
    }

    /**
     * Инициализация тестового списка токенов, полученного из тестового текстового файла разбиением по пробелам и знакам пунктуации.
     * Для обеспечения уникальности токенов список получен через использование промежуточного множества.
     *
     * @param file относительный путь к файлу с тестовыми данными.
     */
    void initTokens(String file) {
        Set<String> stringSet = new TreeSet<>();
        try {
            String s = readString(Paths.get(file));
            String[] tokens = s.split("[\\p{Punct} ]");
            for (int i = 0; i < tokens.length; i++) if (!"".equals(tokens[i])) stringSet.add(tokens[i]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        tokensList.addAll(stringSet);
    }

    /**
     * Тестирование скорости добавления токенов.
     */
    void putTokens() {
        start = System.currentTimeMillis();
        for (int i = 0; i < tokensList.size(); i++) avlTokensTree.put(tokensList.get(i), i);
        System.out.println("AVL put tokens time = " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < tokensList.size(); i++) treeMapTokens.put(tokensList.get(i), i);
        System.out.println("TreeMap put tokens time = " + (System.currentTimeMillis() - start));
    }

    /**
     * Тестирование скорости поиска токенов.
     */
    void findTokens() {
        start = System.currentTimeMillis();
        for (String i : tokensList) avlTokensTree.find(i);
        System.out.println("AVL find tokens time = " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (String i : tokensList) treeMapTokens.get(i);
        System.out.println("TreeMap find tokens time = " + (System.currentTimeMillis() - start));
    }

    /**
     * Тестирование скорости удаления токенов.
     */
    void deleteTokens() {
        start = System.currentTimeMillis();
        for (String i : tokensList) avlTokensTree.delete(i);
        System.out.println("AVL delete tokens time = " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (String i : tokensList) treeMapTokens.remove(i);
        System.out.println("TreeMap delete tokens time = " + (System.currentTimeMillis() - start));
    }

    /**
     * @hidden
     */
    public static void main(String[] args) {
        TestAVLTreeVsTreeMap test = new TestAVLTreeVsTreeMap(50000);
        System.out.println("Sorted data test:");
        test.putSorted();
        test.findSorted();
        test.deleteSorted();

        System.out.println("\nRandom data test:");
        test.putRandom();
        test.findRandom();
        test.deleteRandom();

        System.out.println("\nTokens test:");
        test.initTokens("resources\\wiki.train.tokens");
        test.putTokens();
        test.findTokens();
        test.deleteTokens();
    }
}
