package ru.progwards.java2.lessons.basetypes;

public interface HashValue {
    String stringHash();

    default int getHash() {
        int hash;
        try {
            hash = new IntKey(Integer.parseInt(stringHash())).getHash();
        } catch (NumberFormatException e) {
            hash = new StringKey(stringHash()).getHash();
        }

        return hash;
    }


}

class IntKey {
    private int key;

    public IntKey(int key) {
        this.key = key;
    }


    public int getHash() {
        return key;
    }
}

class StringKey  {
    private String key;

    StringKey(String key) {
        this.key = key;
    }


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