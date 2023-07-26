package ru.progwards.java2.lessons.basetypes;


public class DoubleHashTable<K, V> {

    private class TableEntry<K, V> {


//        private TableEntry(int key, V item) {
//            this.key = new IntKey(key).getHash();
//            this.item = item;
//        }
//
//        private TableEntry(String key, V item) {
//            this.key = new StringKey(key).getHash();
//            this.item = item;
//        }

        private TableEntry(K key, V item) {
            if (key instanceof Integer) this.key = new IntKey((int) key).getHash();
            if (key instanceof String) this.key = new StringKey((String) key).getHash();
            this.item = item;
        }

        private int key;
//        private int index;
        private V item;
        private TableEntry<K, V> next;
        private boolean deleted;
    }


    private TableEntry[] table;
    private int collisionCount;


    private DoubleHashTable() {
        table = new TableEntry[101];
    }

    private DoubleHashTable(int n) {
        table = new TableEntry[n];
    }


    public void add(K key, V value) {
        TableEntry<K, V> tableEntry = new TableEntry<>(key, value);
        int index = getFirstHash(tableEntry.key);
        if (table[index]==null) table[index]= tableEntry;
        else {

        }
    }

    private int getFirstHash(int key) {
        return key % table.length;
    }

    private final static double A = (Math.sqrt(5) - 1) / 2d;

    private int getSecondHash(int key) {
        double d = A * key;
        return (int) (table.length * (d - Math.floor(d)));
    }


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
        System.out.println(nextSize(431));
        System.out.println(StringKey.BKDRHash("abc"));
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


