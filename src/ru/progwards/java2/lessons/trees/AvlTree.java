package ru.progwards.java2.lessons.trees;

import java.util.function.Consumer;

public class AvlTree<K extends Comparable<K>, V> {
    class TreeLeaf<K extends Comparable<K>, V> {
        K key;
        V value;
        TreeLeaf<K, V> parent;
        TreeLeaf<K, V> left;
        TreeLeaf<K, V> right;
        int height;

        public TreeLeaf(K key, V value) {
            this.key = key;
            this.value = value;
        }

        int getBalance() {
            return right.height - left.height;
        }

        void setHeight() {
            height = Math.max(left.height, right.height) + 1;
        }

        // возвращает или узел с ключом key, или узел, к которому надо присоединить узел(key, V)(справа или слева!)
        private TreeLeaf<K, V> find(K key) {
            int cmp = key.compareTo(this.key);
            if (cmp > 0)
                if (right != null) return right.find(key);
                else return this;
            if (cmp < 0)
                if (left != null) return left.find(key);
                else return this;
            return this;
        }

        private void add(TreeLeaf<K, V> leaf) {
//здесь происходит добавление узла в дерево и, при необходимости, балансировка нужными вращениями
            int cmp = leaf.key.compareTo(key);
            if (cmp == 0) this.value = leaf.value;
            if (cmp > 0) {
                right = leaf;
                leaf.parent = this;
            }
            if (cmp < 0) {
                left = leaf;
                leaf.parent = this;
            }
        }

    }

    private TreeLeaf<K, V> root;

    public int getHeight(TreeLeaf leaf) {
        return leaf == null ? 0 : leaf.height;
    }

    // добавить пару ключ-значение, если уже такой ключ есть - заменить
    public void put(K key, V value) {
        TreeLeaf<K, V> leaf = new TreeLeaf<>(key, value);
        if (root == null) root = leaf;
        else root.find(key).add(leaf);
    }


    public void delete(K key) {
    }

    public V find(K key) {
        return null;
    }

    public void change(K oldKey, K newKey) {
    }

    // прямой обход дерева
    public void process(Consumer<TreeLeaf<K, V>> consumer) {
    }
}
