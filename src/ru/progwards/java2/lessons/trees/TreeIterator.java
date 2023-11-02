package ru.progwards.java2.lessons.trees;

import java.util.ArrayList;
import java.util.Iterator;

class TreeIterator<K extends Comparable<K>, V> implements Iterator<BinaryTree.TreeLeaf> {
    private ArrayList<BinaryTree.TreeLeaf> list;
    private int count;

    public TreeIterator(BinaryTree<K, V> bTree) {
        list = new ArrayList<>();
        bTree.getRoot().process(list::add);
        count = 0;
    }

    @Override
    public boolean hasNext() {
        return count < list.size();
    }

    @Override
    public BinaryTree.TreeLeaf next() {
        BinaryTree.TreeLeaf treeLeaf = list.get(count);
        count++;
        return treeLeaf;
    }
}
