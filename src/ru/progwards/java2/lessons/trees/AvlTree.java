package ru.progwards.java2.lessons.trees;

import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;
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

        void fixHeight() {
            height = Math.max(getHeight(left), getHeight(right));
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

        // прямой обход дерева
        public void process(Consumer<TreeLeaf<K, V>> consumer) {
            if (left != null)
                left.process(consumer);
            consumer.accept(this);
            if (right != null)
                right.process(consumer);
        }
    }

    private TreeLeaf<K, V> root;

    public int getHeight(TreeLeaf leaf) {
        return leaf == null ? 0 : leaf.height + 1;
    }

    // добавить пару ключ-значение, если уже такой ключ есть - заменить
    public void put(K key, V value) {
        TreeLeaf<K, V> leaf = new TreeLeaf<>(key, value);
        if (root == null) root = leaf;
        else root.find(key).add(leaf);
        fixHeightAndBalance(leaf.parent);
    }

    public void delete(K key) {
        TreeLeaf<K, V> leafToDel = root.find(key);
        if (leafToDel.key.compareTo(key) != 0) return;// ключ не найден
        if (leafToDel.height > 0) { // (leafToDel.left != null || leafToDel.right != null)
            boolean bal = leafToDel.getBalance() > 0;
            TreeLeaf<K, V> replace = bal ? findMin(leafToDel.right) : findMax(leafToDel.left);
            TreeLeaf<K, V> replaceParent = replace.parent;
            K replaceParentKey = replace.parent.key;
            leafToDel.key = replace.key;
            leafToDel.value = replace.value;
            if (bal) {
                if (replaceParentKey.compareTo(replace.key) > 0) replaceParent.left = replace.right;
                else replaceParent.right = replace.right;
                if (replace.right != null) replace.right.parent = replaceParent;
            } else {
                if (replaceParentKey.compareTo(replace.key) > 0) replaceParent.left = replace.left;
                else replaceParent.right = replace.left;
                if (replace.left != null) replace.left.parent = replaceParent;
            }
            fixHeightAndBalance(replaceParent);
        } else { // узел не имеет потомков
            if (leafToDel == root) {
                root = null;
                return;
            }
            TreeLeaf<K, V> parent = leafToDel.parent;
            if (parent.key.compareTo(leafToDel.key) > 0) parent.left = null;
            else parent.right = null;
            fixHeightAndBalance(parent);
        }
    }

    private void balance(TreeLeaf<K, V> leaf) {
// Проверка наличия и вида дисбаланса.
        if (leaf.getBalance() == -2) {
            if (leaf.left.getBalance() <= 0) rightRotation(leaf);
            else rightRotation(leftRotation(leaf.left).parent);// большое правое вращение (МЛВ + МПВ)
        }
        if (leaf.getBalance() == 2) {
            if (leaf.right.getBalance() >= 0) leftRotation(leaf);
            else leftRotation(rightRotation(leaf.right).parent);// большое правое вращение (МПВ + МЛВ)
        }
    }

    // Малое левое вращение (МЛВ). Bозвращает новую (локальную) вершину дерева
    private TreeLeaf<K, V> leftRotation(TreeLeaf<K, V> leaf) {
        TreeLeaf<K, V> b = leaf.right;
        TreeLeaf<K, V> c = b.left;
        leaf.right = c;
        leaf.fixHeight();
        b.left = leaf;
        b.fixHeight();
        b.parent = leaf.parent;
        if (b.parent != null) {
            int com = b.key.compareTo(b.parent.key);
            if (com > 0) b.parent.right = b;
            if (com < 0) b.parent.left = b;
        }
        leaf.parent = b;
        if (c != null) c.parent = leaf;
        if (leaf == root) root = b;
        return b;
    }

    // Малое правое вращение (МПВ). Bозвращает новую (локальную) вершину дерева
    private TreeLeaf<K, V> rightRotation(TreeLeaf<K, V> leaf) {
        TreeLeaf<K, V> b = leaf.left;
        TreeLeaf<K, V> c = b.right;
        leaf.left = c;
        leaf.fixHeight();
        b.right = leaf;
        b.fixHeight();
        b.parent = leaf.parent;
        if (b.parent != null) {
            int com = b.key.compareTo(b.parent.key);
            if (com > 0) b.parent.right = b;
            if (com < 0) b.parent.left = b;
        }
        leaf.parent = b;
        if (c != null) c.parent = leaf;
        if (leaf == root) root = b;
        return b;
    }

    // коррекция высот узлов и, при необходимости, балансировка. Вверх по всем родителям нового узла, до корня.
    private void fixHeightAndBalance(TreeLeaf<K, V> leaf) {
        TreeLeaf<K, V> current = leaf;
        while (current != null) {
            current.fixHeight();
            balance(current);
            current = current.parent;
        }
    }

    private TreeLeaf<K, V> findMin(TreeLeaf<K, V> leaf) {
        if (leaf.left == null) return leaf;
        return findMin(leaf.left);
    }

    private TreeLeaf<K, V> findMax(TreeLeaf<K, V> leaf) {
        if (leaf.right == null) return leaf;
        return findMax(leaf.right);
    }

    public V find(K key) throws NoSuchElementException {
        TreeLeaf<K, V> leaf = root.find(key);
        if (leaf.key.compareTo(key) == 0)
            return leaf.value;
        else return null;//throw new NoSuchElementException("Entry with the key " + key + " is not found.");
    }

    public void change(K oldKey, K newKey) throws NoSuchElementException {
        TreeLeaf<K, V> leaf = root.find(oldKey);
        if (leaf.key.compareTo(oldKey) == 0) {
            V value = leaf.value;
            delete(oldKey);
            put(newKey, value);
        } else throw new NoSuchElementException("Entry with the key " + oldKey + " is not found.");
    }


    public static void main(String[] args) {
        AvlTree<Integer, Integer> myPunyTree = new AvlTree<>();
        for (int i = 0; i < 10; i++) {
//            int rdm = Math.abs(ThreadLocalRandom.current().nextInt() % 50);
//            myPunyTree.put(rdm, rdm);
//            myPunyTree.put(i, i);
        }
        int[] array = {5, 11, 3, 8, 6, 4, 0, 2, 7, 1, 9, 67, 34};
        for (int i = 0; i < array.length; i++) {
            myPunyTree.put(array[i], array[i]);
        }
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
            myPunyTree.delete(array[i]);

        }
//        myPunyTree.put(12, 12);
//        myPunyTree.put(11, 11);
//        myPunyTree.put(10, 10);
//        myPunyTree.put(6, 6);
//
//        myPunyTree.put(5, 5);

//        myPunyTree.put(9, 9);
//        myPunyTree.put(8, 8);
//        myPunyTree.root.process(v -> System.out.println(v.key));
//        try {
//            System.out.println(myPunyTree.find(3));
//            System.out.println(myPunyTree.find(69));
//            System.out.println(myPunyTree.find(6));
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
        System.out.println();
//        myPunyTree.delete(7);
//        myPunyTree.delete(8);
//        myPunyTree.delete(9);

        System.out.println();
    }
}

