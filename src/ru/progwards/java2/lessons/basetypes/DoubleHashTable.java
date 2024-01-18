package ru.progwards.java2.lessons.basetypes;

import java.util.Iterator;

public class DoubleHashTable<K, V> implements Iterable<V> {

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

    class TableEntry<K, V> {

        private TableEntry(K key, V item) {
            this.key = key;
            this.item = item;
            if (key instanceof Number) this.basicKey = new IntKey(((Number) key).intValue()).getHash();
            else if (key instanceof String) this.basicKey = new StringKey((String) key).getHash();
            else this.basicKey = new IntKey(key.hashCode()).getHash();
            this.trueIndex = getFirstHash(basicKey, table.length);
        }

        public V getItem() {
            return item;
        }

        private int basicKey;
        private int trueIndex;
        private K key;
        private V item;
        private boolean deleted;
    }

    private TableEntry<K, V>[] table;
    private int collisionCount;
    private int size;


    public DoubleHashTable() {
        table = new TableEntry[101];
    }

    public void add(K key, V value) {
        if (value != null) {
            TableEntry<K, V> tableEntry = new TableEntry<>(key, value);
            putEntry(tableEntry, table);
            size++;
            if (collisionCount * 10 > table.length) {
                expandTable();
            }
        } else {
            System.out.println("Добавление значения null с ключом \"" + key + "\" проигнорованно.");
        }
    }

    public V get(K key) {
        TableEntry<K, V> nullEntry = new TableEntry<>(key, null);
        int guessIndex = nullEntry.trueIndex;
        while (table[guessIndex] != null) {
            if (table[guessIndex].key.equals(nullEntry.key)) {
                return (V) table[guessIndex].getItem();
            }
            guessIndex = getSecondHash(guessIndex, table.length);
        }
        return null;
    }

    public void remove(K key) {
        TableEntry<K, V> nullEntry = new TableEntry<>(key, null);
        int guessIndex = nullEntry.trueIndex;
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

    private void expandTable() {
        int newTableSize = nextSize(table.length);
        TableEntry<K, V>[] newTable = new TableEntry[newTableSize];
        collisionCount = 0;
        for (TableEntry tableEntry : table) {
            if (tableEntry != null) {
                tableEntry.trueIndex = getFirstHash(tableEntry.basicKey, newTableSize); // изменение trueIndex под новый размер таблицы
                putEntry(tableEntry, newTable);
            }
        }
        table = newTable;
    }

    private void putEntry(TableEntry tableEntry, TableEntry[] table) {
        if (!tableEntry.deleted) { // удалённые элементы при расширении таблицы игнорируются
            int guessIndex = tableEntry.trueIndex;
            while (table[guessIndex] != null) {
                if (table[guessIndex].key.equals(tableEntry.key)) {
                    System.out.println("Элемент E<key, value>: (" + table[guessIndex].key + ", " + table[guessIndex].item +
                            ") заменён на новый, (" + tableEntry.key + ", " + tableEntry.item + ").");
                    break;
                }
                guessIndex = getSecondHash(guessIndex, table.length);
                collisionCount++;
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
//        DoubleHashTable<String, Integer> testDel = new DoubleHashTable<>();
////        System.out.println(nextSize(431));
////        System.out.println(StringKey.BKDRHash("abc"));
//        testDel.add("hello", 21);
//        testDel.add("hello1", 22);
//        testDel.add("hello2", 23);
//        testDel.add("hello3", 24);
//        testDel.remove("hello2");
//        System.out.println(testDel.get("hello2"));

//        DoubleHashTable<Integer, Integer> test2 = new DoubleHashTable<>();
//        test2.add(10, 1);
//        test2.add(111, 2);
//        test2.add(212, 3);
//        test2.add(313, 4);
//        test2.remove(313);
//        test2.add(414, 5);
//        test2.add(515, 6);
//        test2.add(616, 7);
//        test2.add(23, null);
//        System.out.println(test2.get(212));
//        test2.change(212, 717);

//        System.out.println(test2.get(717));
//        System.out.println("size = " + test2.size() + ";\n");
//        int sum = 0;
//        for (Integer t : test2) {
//            sum += t;
//            System.out.print(t + "  ");
//        }
//        System.out.println("\n" + sum);
//        for (int i = 0; i < test2.table.length; i++) {
//            if (test2.table[i] != null) System.out.println(test2.get((int) test2.table[i].key));
//        }

        DoubleHashTable<KeyInteger, String> list = new DoubleHashTable<>();
        for(int i=0; i<1000; i++) {
            System.out.println(i);
            list.add(new KeyInteger(i), "i="+i);
        }
    }
}

class IntKey implements HashValue {
    private int key;

    public IntKey(int key) {
        this.key = key;
    }

    @Override
    public int getHash() {
        return key;
    }
}

class StringKey implements HashValue {
    private String key;

    StringKey(String key) {
        this.key = key;
    }

    @Override
    public int getHash() {
        return (int) (BKDRHash(key));
    }

    public static long BKDRHash(String str) {
        long seed = 131; // 31 131 1313 13131 131313 etc..
        long hash = 0;

        for (int i = 0; i < str.length(); i++) {
            hash = unsignedInt((hash * seed) + str.charAt(i));
        }
        return hash;
    }

    static final long UINT_MAX = (long) Integer.MAX_VALUE * 2;

    static long unsignedInt(long n) {
        return n % UINT_MAX;
    }
}


class KeyInteger{
    int i;

    public KeyInteger(int i) {
        this.i = i;
    }
}