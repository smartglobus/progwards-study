package ru.progwards.java1.lessons.sets;

import java.util.HashSet;
import java.util.Set;

public class SetOperations {

    public static Set<Integer> union(Set<Integer> set1, Set<Integer> set2){
        Set<Integer> uniSet = new HashSet<>(set1);
        uniSet.addAll(set2);
        return uniSet;
    }

    public static Set<Integer> intersection(Set<Integer> set1, Set<Integer> set2){
        Set<Integer> interSet = new HashSet<>(set1);
        interSet.retainAll(set2);
        return interSet;
    }

    public static Set<Integer> difference(Set<Integer> set1, Set<Integer> set2){
        Set<Integer> diffSet = new HashSet<>(set1);
        diffSet.removeAll(set2);
        return diffSet;
    }

    public static Set<Integer> symDifference(Set<Integer> set1, Set<Integer> set2){
        Set<Integer> symDiffSet = union(set1,set2);
        symDiffSet.removeAll(intersection(set1,set2));
        return symDiffSet;
    }

    public static void main(String[] args) {
        Set<Integer> s1 = new HashSet<>();
        s1.add(1);
        s1.add(2);
        s1.add(3);
        s1.add(4);
        s1.add(5);
        s1.add(6);
        s1.add(7);

        Set<Integer> s2 = new HashSet<>();
        s2.add(4);
        s2.add(5);
        s2.add(6);
        s2.add(7);
        s2.add(8);
        s2.add(9);
        s2.add(10);


        for (Integer a : union(s1,s2)) System.out.println(a);
        System.out.println("");
        for (Integer a : intersection(s1,s2)) System.out.println(a);
        System.out.println("");
        for (Integer a : difference(s1,s2)) System.out.println(a);
        System.out.println("");
        for (Integer a : symDifference(s1,s2)) System.out.println(a);
    }
}
