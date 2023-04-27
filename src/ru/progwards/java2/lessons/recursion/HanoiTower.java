package ru.progwards.java2.lessons.recursion;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

public class HanoiTower {
    private int size;
    private int pos;
    private Map<Integer, ArrayDeque<Integer>> map = new HashMap<>();
    private ArrayDeque<Integer> peg0 = new ArrayDeque<>();
    private ArrayDeque<Integer> peg1 = new ArrayDeque<>();
    private ArrayDeque<Integer> peg2 = new ArrayDeque<>();
    private boolean trace;

    public HanoiTower(int size, int pos) {
        this.size = size;
        this.pos = pos;
        map.put(0, peg0);
        map.put(1, peg1);
        map.put(2, peg2);
        for (int i = size; i >= 1; i--) {
            map.get(pos).offerFirst(i);
        }
    }

    public void hanoiTower(int to) {
        if (to == pos || size == 0) return;
        if (size == 1) {
            map.get(to).offerFirst(map.get(pos).poll());
            return;
        }
        if (size % 2 == 1) move(pos, 3 - pos - to);// для нечётного size меняется направление обхода
        else move(pos, to);
    }

    public void move(int from, int to) {
        int sparePeg = 3 - from - to;

        map.get(sparePeg).offerFirst(map.get(from).poll());
        print();
        map.get(to).offerFirst(map.get(from).poll());
        print();
        map.get(to).offerFirst(map.get(sparePeg).poll());
        print();

        if (peg0.size() == size || peg1.size() == size || peg2.size() == size) return;// выход при достижении size

        // выбор, какое из больших колец передвигать
        if (map.get(sparePeg).isEmpty() || (!map.get(from).isEmpty() && map.get(sparePeg).peekFirst() > map.get(from).peekFirst()))
            map.get(sparePeg).offerFirst(map.get(from).poll());
        else map.get(from).offerFirst(map.get(sparePeg).poll());
        print();
        move(to, sparePeg);
    }


    void print() {
        if (trace) {
            ArrayDeque<Integer> Clone0 = peg0.clone();
            ArrayDeque<Integer> Clone1 = peg1.clone();
            ArrayDeque<Integer> Clone2 = peg2.clone();

            for (int i = 1; i <= size; i++) {
                System.out.println(numFormat(Clone0.pollFirst()) + " " + numFormat(Clone1.pollFirst()) + " " + numFormat(Clone2.pollFirst()));
            }
            System.out.println("=================");
        }
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
    }

    public static void main(String[] args) {
        HanoiTower ht = new HanoiTower(3, 2);
        ht.setTrace(true);
        ht.print();
        ht.hanoiTower(2);
        ht.print();
    }
}
