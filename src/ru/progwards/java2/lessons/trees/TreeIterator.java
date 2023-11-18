package ru.progwards.java2.lessons.trees;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Итератор для класса {@link BinaryTree}. Основывается на методе {@link BinaryTree.TreeLeaf#process(java.util.function.Consumer)},
 * возвращающем сортированный по возрастанию ключей список содержимого дерева.
 * @param <K> Ключ, с которым объект размещается в двоичном дереве поиска BinaryTree.
 * @param <V> Значение (объект), размещаемое в дереве с ключом K.
 */

public class TreeIterator<K extends Comparable<K>, V> implements Iterator<BinaryTree.TreeLeaf> {
    private ArrayList<BinaryTree.TreeLeaf> list;
    private int count;

    /**
     * Инициализирует новый итератор для выбранного дерева
     * @param bTree двоичное дерево поиска
     */
    public TreeIterator(BinaryTree<K, V> bTree) {
        list = new ArrayList<>();
        bTree.getRoot().process(list::add);
        count = 0;
    }

    /**
     * Проверяет, есть ли следующий элемент
     * @return есть ли сдедующий элемент
     */
    @Override
    public boolean hasNext() {
        return count < list.size();
    }

    /**
     * Возвращает следующий элемент дерева
     * @return следующий элемент
     */
    @Override
    public BinaryTree.TreeLeaf next() {
        BinaryTree.TreeLeaf treeLeaf = list.get(count);
        count++;
        return treeLeaf;
    }
}
