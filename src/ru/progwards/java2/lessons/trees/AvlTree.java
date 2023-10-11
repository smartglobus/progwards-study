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
            return getHeight(right) - getHeight(left);
        }

        void setHeight() {
            height = Math.max(getHeight(left), getHeight(right)) + 1;
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

        // коррекция высот узлов и, при необходимости, балансировка. Вверх по всем родителям нового узла, до корня.
//        correctHeight(leaf);
        TreeLeaf<K, V> prnt = leaf.parent;
        while (prnt != null) {
            prnt.setHeight();
            balance(prnt);
            prnt = prnt.parent;
        }


    }

    TreeLeaf<K, V> balance(TreeLeaf<K, V> leaf) {
// проверка наличия и вида дисбаланса
// возврат - новая (локальная) вершина дерева
        if (leaf.getBalance() == -2 && leaf.left.getBalance() <= 0) {
            // малое правое вращение
            TreeLeaf<K, V> b = leaf.left;
            TreeLeaf<K, V> c = b.right;
            leaf.left = c;
            leaf.setHeight();
            b.right = leaf;
            b.setHeight();
            b.parent = leaf.parent;
            leaf.parent = b;
            c.parent = leaf;
            if (leaf == root) root = b;
            return b;
        }

        if (leaf.getBalance() == 2 && leaf.right.getBalance() >= 0) {
            // малое левое вращение
            TreeLeaf<K, V> b = leaf.right;
            TreeLeaf<K, V> c = b.left;
            leaf.right = c;
            leaf.setHeight();
            b.left = leaf;
            b.setHeight();
            b.parent = leaf.parent;
            leaf.parent = b;
            c.parent = leaf;
            if (leaf == root) root = b;
            return b;
        }

        if (leaf.getBalance() == -2 && leaf.left.getBalance() > 0) {
            // большое правое вращение
        }

        if (leaf.getBalance() == 2 && leaf.right.getBalance() > 0) {
            // большое левое вращение
        }
        return leaf;
    }


    void correctHeight(TreeLeaf<K, V> leaf) {// ??? нужен ли этот отдельный метод?
        TreeLeaf<K, V> prnt = leaf.parent;
        while (prnt != null) {
            prnt.setHeight();
            prnt = prnt.parent;
        }
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

    public static void main(String[] args) {
        AvlTree<Integer, Integer> myPunyTree = new AvlTree<>();
        myPunyTree.put(10, 10);
        myPunyTree.put(7, 7);
        myPunyTree.put(12, 12);
        myPunyTree.put(6, 6);
        myPunyTree.put(8, 8);
        myPunyTree.put(5, 5);
        System.out.println();
    }
}
