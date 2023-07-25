package ru.progwards.java2.lessons.basetypes;

import java.util.*;

public class BiDirList<T> implements Iterable<T> {

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            listItem<T> curr = head;

            @Override
            public boolean hasNext() {
                return curr != null;
            }

            @Override
            public T next() {
                T result = curr.item;
                curr = curr.next;
                return result;
            }
        };
    }

    class listItem<T> {
        private T item;
        private listItem<T> prev;
        private listItem<T> next;

        listItem(T item) {
            this.item = item;
            size++;
        }

        public T getItem() {
            return item;
        }

        public listItem<T> getPrev() {
            return prev;
        }

        public listItem<T> getNext() {
            return next;
        }

        void setPrev(listItem prev) {
            this.prev = prev;
        }

        void setNext(listItem next) {
            this.next = next;
        }
    }


    private listItem<T> head;
    private listItem<T> tail;
    private int size;


    public listItem<T> getHead() {
        return head;
    }

    public listItem<T> getTail() {
        return tail;
    }


    public void addLast(T item) {
        listItem<T> newEntry = new listItem<>(item);
        if (head == null) {
            head = newEntry;
            tail = newEntry;
        } else {
            tail.setNext(newEntry);
            newEntry.setPrev(tail);
            tail = newEntry;
        }
    }

    public void addFirst(T item) {
        listItem<T> newEntry = new listItem<>(item);
        if (head == null) {
            head = newEntry;
            tail = newEntry;
        } else {
            head.setPrev(newEntry);
            newEntry.setNext(head);
            head = newEntry;
        }
    }

    // удаляет один, первый из подходящих, элемент из списка
    public void remove(T item) {
        listItem<T> current = head;
        while (current != null) {
            if (current.item.equals(item)) {
                if (current.next == null) {
                    current.prev.next = null;
                    tail = current.prev;
                    size--;
                    break;
                }
                if (current.prev == null) {
                    current.next.prev = null;
                    current = current.next;
                    head = current;
                    size--;
                    break;
                }
                current.prev.next = current.next;
                current.next.prev = current.prev;
                size--;
                break;
            }
            current = current.next;
        }
    }

    // удаляет все подходящие элементы из списка
    public void removeAll(T item) {
        listItem<T> current = head;
        while (current != null) {
            if (current.item.equals(item)) {
                if (current.next == null) {
                    current.prev.next = null;
                    tail = current.prev;
                    size--;
                    break;
                }
                if (current.prev == null) {
                    current.next.prev = null;
                    current = current.next;
                    head = current;
                    size--;
                    continue;
                }
                current.prev.next = current.next;
                current.next.prev = current.prev;
                size--;
            }
            current = current.next;
        }
    }

    public T at(int i) {
        listItem<T> current = head;
        for (int j = 0; j < size; j++) {
            if (j == i) return current.item;
            current = current.next;
        }
        return null;
    }

    public static <T> BiDirList<T> from(T[] array) {
        BiDirList<T> result = new BiDirList<>();
        for (T t : array) result.addLast(t);
        return result;
    }

    public static <T> BiDirList<T> of(T... array) {
        return from(array);
    }

    public void toArray(T[] array) {
        if (array.length != size)
            System.out.println("Размер массива не совпадает с размером списка!\nРазмер списка = " + size());
        listItem<T> current = head;
        for (int i = 0; i < size && i < array.length; i++) {
            array[i] = current.item;
            current = current.next;
        }
    }

    public int size() {
        return size;
    }


    public static void main(String[] args) {
        BiDirList<Integer> testBDL = new BiDirList<>();
        testBDL.addLast(2);
        testBDL.addLast(3);
        testBDL.addLast(4);
        testBDL.addLast(3);
        testBDL.addLast(4);
        testBDL.addLast(3);
        testBDL.addFirst(1);
        testBDL.addFirst(3);

        System.out.println("head = " + testBDL.getHead().getItem() + "; tail = " + testBDL.getTail().getItem() + "; size = " + testBDL.size());
        for (Integer i : testBDL) System.out.println(i);
        System.out.println();
        System.out.println("at(i) = " + testBDL.at(10));
//        testBDL.remove(3);
//        testBDL.remove(3);
//        testBDL.remove(3);
//        testBDL.removeAll(3);
        for (Integer i : testBDL) System.out.println(i);
        System.out.println("head = " + testBDL.getHead().getItem() + "; tail = " + testBDL.getTail().getItem() + "; size = " + testBDL.size());
        String[] strings = {"one", "two", "three", "four", "five"};
        BiDirList<String> stringBDL = from(strings);
        System.out.println();
        for (String i : stringBDL) System.out.println(i);
        System.out.println("head = " + stringBDL.getHead().getItem() + "; tail = " + stringBDL.getTail().getItem() + "; size = " + stringBDL.size());

        stringBDL.toArray(strings);


        BiDirList<String> ofVarargBDL = of("var", "one", "two", "three", "four", "five", "args");
        System.out.println();
        for (String i : ofVarargBDL) System.out.println(i);
        System.out.println("head = " + ofVarargBDL.getHead().getItem() + "; tail = " + ofVarargBDL.getTail().getItem() + "; size = " + ofVarargBDL.size());
        String[] sa = new String[5];
        ofVarargBDL.toArray(sa);
        System.out.println(Arrays.toString(sa));
    }
}
