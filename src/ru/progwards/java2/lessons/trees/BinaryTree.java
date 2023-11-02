package ru.progwards.java2.lessons.trees;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class BinaryTree<K extends Comparable<K>, V> {

    private static final String KEYEXIST = "Key already exist";
    private static final String KEYNOTEXIST = "Key not exist";

    class TreeLeaf<K extends Comparable<K>, V> {
        K key;
        V value;
        TreeLeaf<K, V> parent;
        TreeLeaf<K, V> left;
        TreeLeaf<K, V> right;

        public TreeLeaf(K key, V value) {
            this.key = key;
            this.value = value;
        }

        private TreeLeaf<K, V> find(K key) {
            int cmp = key.compareTo(this.key);
            if (cmp > 0)
                if (right != null)
                    return right.find(key);
                else
                    return this;
            if (cmp < 0)
                if (left != null)
                    return left.find(key);
                else
                    return this;
            return this;
        }

        void add(TreeLeaf<K, V> leaf) throws TreeException {
            int cmp = leaf.key.compareTo(key);
            if (cmp == 0)
                throw new TreeException(KEYEXIST);
            if (cmp > 0) {
                right = leaf;
                leaf.parent = this;
            } else {
                left = leaf;
                leaf.parent = this;
            }
        }

        void delete() throws TreeException {
            if (parent.right == this) {
                parent.right = right;
                if (right != null)
                    right.parent = parent;
                if (left != null)
                    parent.find(left.key).add(left);
            } else {
                parent.left = left;
                if (left != null)
                    left.parent = parent;
                if (right != null)
                    parent.find(right.key).add(right);

            }
        }

        public String toString() {
            return "(" + key + ", " + value + ")";
        }

        public void process(Consumer<TreeLeaf<K, V>> consumer) {
            if (left != null)
                left.process(consumer);
            consumer.accept(this);
            if (right != null)
                right.process(consumer);
        }
    }

    private TreeLeaf<K, V> root;

    public TreeLeaf<K, V> getRoot() {
        return root;
    }

    public V find(K key) {
        if (root == null)
            return null;
        TreeLeaf<K, V> found = root.find(key);
        return found.key.compareTo(key) == 0 ? (V) found.value : null;
    }

    public void add(TreeLeaf<K, V> leaf) throws TreeException {
        if (root == null)
            root = leaf;
        else
            root.find(leaf.key).add(leaf);
    }

    public void add(K key, V value) throws TreeException {
        add(new TreeLeaf<>(key, value));
    }

    public void delete(K key) throws TreeException {
        internalDelete(key);
    }

    public TreeLeaf<K, V> internalDelete(K key) throws TreeException {
        if (root == null) {
        }
        return null;
    }

    static class TreeException extends Exception {
        TreeException(String message) {
            super(message);
        }
    }

    public TreeIterator<K, V> getIterator() {
        return new TreeIterator<>(this);
    }

    public static void main(String[] args) {
        BinaryTree<Integer, Integer> btree = new BinaryTree<>();

        try {
            for (int i = 0; i < 10; i++) {
                int rdm = Math.abs(ThreadLocalRandom.current().nextInt() % 500);
                btree.add(rdm, rdm);
            }
        } catch (TreeException e) {
            e.printStackTrace();
        }
        for (TreeIterator<Integer, Integer> treeIterator = btree.getIterator(); treeIterator.hasNext(); ) {
            System.out.println(treeIterator.next().key);
        }
    }
}
