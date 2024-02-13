package ru.progwards.java2.lessons.basetypes;

import java.util.Iterator;

public class DoubleHashTable<K extends HashValue, V> implements Iterable<V> {

    @Override
    public Iterator<V> iterator() {
        return new Iterator<>() {
            TableEntry<K, V>[] arr = table;
            int i = 0;

            @Override
            public boolean hasNext() {
                while (i < arr.length) {
                    if (arr[i] != null && arr[i].item != null) {
                        return true;
                    }
                    i++;
                }
                return false;
            }

            @Override
            public V next() {
                V item = arr[i].item;
                i++;
                return item;
            }
        };
    }

    class TableEntry<K extends HashValue, V> {
        private int basicKey;
        private K key;
        private V item;
        private boolean deleted;

        private TableEntry(K key, V item) {
            this.key = key;
            this.item = item;
            this.basicKey = key.getHash();
        }

        public V getItem() {
            return item;
        }
    }

    private TableEntry<K, V>[] table;
    private int collisionCount;
    private int size;


    public DoubleHashTable() {
        table = new TableEntry[101];
    }

    public void add(K key, V value) {
        TableEntry<K, V> tableEntry = new TableEntry<>(key, value);
        putEntry(tableEntry, table);
        size++;
    }

    public V get(K key) {
        TableEntry<K, V> nullEntry = new TableEntry<>(key, null);
        int guessIndex = getFirstHash(nullEntry.basicKey, table.length);
        while (table[guessIndex] != null) {
            if (table[guessIndex].key.equals(nullEntry.key)) {
                return table[guessIndex].getItem();
            }
            guessIndex = getSecondHash(guessIndex, table.length);
        }
        return null;
    }

    public void remove(K key) {
        TableEntry<K, V> nullEntry = new TableEntry<>(key, null);
        int guessIndex = getFirstHash(nullEntry.basicKey, table.length);
        while (table[guessIndex] != null) {
            if (table[guessIndex].key.equals(nullEntry.key)) {
                table[guessIndex].item = null;
                table[guessIndex].deleted = true;
                size--;
            }
            guessIndex = getSecondHash(guessIndex, table.length);
        }
    }

    public void change(K key1, K key2) {
        V item = get(key1);
        if (item != null) {
            remove(key1);
            add(key2, item);
        }
    }

    public int size() {
        return size;
    }

    private TableEntry<K, V>[] expandTable() {
        int newTableSize = nextSize(table.length);
        TableEntry<K, V>[] newTable = new TableEntry[newTableSize];
        collisionCount = 0;
        for (TableEntry tableEntry : table) {
            if (tableEntry != null) {
                putEntry(tableEntry, newTable);
            }
        }
        table = newTable;
        return newTable;
    }

    private void putEntry(TableEntry tableEntry, TableEntry[] table) {
        if (!tableEntry.deleted) { // удалённые элементы при расширении таблицы игнорируются
            int guessIndex = getFirstHash(tableEntry.basicKey, table.length);
            while (table[guessIndex] != null) {
                if (table[guessIndex].key.equals(tableEntry.key)) {
               // Действия при добавлении элемента с повторяющимся ключом не указаны, метод void, поэтому сделал println
                    System.out.println("Элемент E<key, value>: (" + table[guessIndex].key + ", " + table[guessIndex].item +
                            ") заменён на новый, (" + tableEntry.key + ", " + tableEntry.item + ").");
                    break;
                }
                guessIndex = getSecondHash(guessIndex, table.length);
                collisionCount++;
                if (collisionCount * 10 > table.length) {
                    table = expandTable();
                    guessIndex = getFirstHash(tableEntry.basicKey, table.length);
                }
            }
            table[guessIndex] = tableEntry;
        }
    }

    private int getFirstHash(int key, int tableSize) {
        return key % tableSize;
    }

    private int getSecondHash(int key, int tableSize) {
        double d = A * key;
        return (int) (tableSize * (d - Math.floor(d)));
    }

    private final static double A = (Math.sqrt(5) - 1) / 2d;

    private static int nextSize(int currSize) {
        int next = currSize * 2 - 1;
        boolean isPrime = false;
        while (!isPrime) {
            next += 2;
            for (int i = 3; i <= next / 2; i += 2) {
                if (next % i == 0) break;
                if (i == next / 2) isPrime = true;
            }
        }
        return next;
    }

    public static void main(String[] args) {

        DoubleHashTable<KeyInteger, String> list = new DoubleHashTable<>();
        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
            KeyInteger check = new KeyInteger(i);
            list.add(check, "i=" + i);
        }
//        list.add(new KeyInteger(0), "0");
//        list.add(new KeyInteger(101), "101");
//        list.add(new KeyInteger(211), "211");
//        list.add(new KeyInteger(431), "431");
//        list.add(new KeyInteger(863), "863");

        DoubleHashTable<Humanoid, Integer> humanList = new DoubleHashTable<>();
        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
            Humanoid humanoid = new Humanoid("Humanoid " + i);
            humanList.add(humanoid, null);
        }
    }
}


class KeyInteger implements HashValue {
    int i;

    public KeyInteger(int i) {
        this.i = i;
    }

    @Override
    public int getHash() {
        return getHashFromNumber(i);
    }
}

class Humanoid implements HashValue {
    String name;

    public Humanoid(String name) {
        this.name = name;
    }

    @Override
    public int getHash() {
        return getHashFromString(name);
    }
}