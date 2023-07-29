package ru.progwards.java2.lessons.basetypes;


public class DoubleHashTable<K, V> {

    private class TableEntry<K, V> {

        private TableEntry(K key, V item) {
            this.key = key;
            this.item = item;
            if (key instanceof Integer) this.basicKey = new IntKey((int) key).getHash();
            if (key instanceof String) this.basicKey = new StringKey((String) key).getHash();
            this.trueIndex = getFirstHash(basicKey, table.length);
        }

//        public void setNext(TableEntry<K, V> next) {
//            this.next = next;
//        }

        private int basicKey;
        private int trueIndex;
        private K key;
        private V item;
        // private TableEntry<K, V> next;// оно нужно?????
        private boolean deleted;
    }


    private TableEntry[] table;
    private int collisionCount;
    private int size;


    private DoubleHashTable() {
//        this(101);
        table = new TableEntry[101];
    }

//    private DoubleHashTable(int n) {
//        table = new TableEntry[n];
//    }


    public void add(K key, V value) {
        TableEntry<K, V> tableEntry = new TableEntry<>(key, value);
        putEntry(tableEntry, table);
        size++;
        if (collisionCount * 10 > table.length) {
            expandTable();
        }
    }

    private void expandTable() {
        int newTableSize = nextSize(table.length);
        TableEntry[] newTable = new TableEntry[newTableSize];
        collisionCount = 0;
        for (TableEntry tableEntry : table) {
            if (tableEntry != null) {
                tableEntry.trueIndex = getFirstHash(tableEntry.basicKey, newTableSize);// изменение trueIndex под новый размер таблицы
                putEntry(tableEntry, newTable);
            }
        }
        table = newTable;
    }

    private void putEntry(TableEntry tableEntry, TableEntry[] table) {
        int guessIndex = tableEntry.trueIndex;
        while (table[guessIndex] != null) {
            guessIndex = getSecondHash(guessIndex, table.length);
            collisionCount++;
        }
        table[guessIndex] = tableEntry;
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
        DoubleHashTable<String, Integer> testDel = new DoubleHashTable<>();
//        System.out.println(nextSize(431));
//        System.out.println(StringKey.BKDRHash("abc"));
        testDel.add("hello", 25);
        testDel.add("hello", 25);
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


