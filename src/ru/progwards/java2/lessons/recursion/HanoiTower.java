package ru.progwards.java2.lessons.recursion;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

public class HanoiTower {
    private int size;
    private int pos;
    private Map<Integer, ArrayDeque<Integer>> map = new HashMap<>();
    private ArrayDeque<Integer> one = new ArrayDeque<>();
    private ArrayDeque<Integer> two = new ArrayDeque<>();
    private ArrayDeque<Integer> three = new ArrayDeque<>();
    private boolean trace;

    public HanoiTower(int size, int pos) {
        this.size = size;
        this.pos = pos;
        map.put(1, one);
        map.put(2, two);
        map.put(3, three);
        for (int i = size; i >= 1; i--) {
            map.get(pos).add(i);
        }


    }

    public void move(int from, int to){

    }



    void print() {
        ArrayDeque<Integer> oneClone = one.clone();
        ArrayDeque<Integer> twoClone = two.clone();
        ArrayDeque<Integer> threeClone = three.clone();

        for (int i = 1; i <= size; i++) {
            System.out.println(numFormat(oneClone.pollLast()) + " " + numFormat(twoClone.pollLast()) + " " + numFormat(threeClone.pollLast()));
        }
        System.out.println("=================");
    }

    private String numFormat(Integer n) {
        if (n == null) return "  I  ";
        if (n > 0 && n < 10) return "<00" + n + ">";
        if (n >= 10 && n < 100) return "<0" + n + ">";
        if (n >= 100 && n < 1000) return "<" + n + ">";
        return "<ovl>";
    }

    void setTrace(boolean on) {
        trace = on;
        if (on) print();//?????
    }

    public static void main(String[] args) {
        HanoiTower ht = new HanoiTower(4, 2);
        ht.print();
    }
}
