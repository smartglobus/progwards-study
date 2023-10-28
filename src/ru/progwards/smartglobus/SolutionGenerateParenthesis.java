package ru.progwards.smartglobus;

import java.util.ArrayList;
import java.util.List;

public class SolutionGenerateParenthesis {
    static String prntsSeq = "";
    static int num;

    public static List<String> generateParenthesis(int n) {
        List<String> generateParenthesis = new ArrayList<>();
        num = n;
        generateParenthesis.add(parentesis(n, n));


        return generateParenthesis;
    }

    static String parentesis(int open, int close) {
        if (num == 0) return prntsSeq;
        prntsSeq += parentesis(num - 1, num);


        return null;
    }

    public static void main(String[] args) {
        generateParenthesis(3);
    }
}


//  You are given the heads of two sorted linked lists list1 and list2.
//        Merge the two lists in a one sorted list. The list should be made by splicing together the nodes of the first two lists.
//        Return the head of the merged linked list.



class Solution {
    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {

        if (list1 == null) return list2;
        if (list2 == null) return list1;
        if (list1.val <= list2.val) {
            ListNode list1next = list1.next;
            if (list1next == null) {
                list1.next = list2;
                return list1.next;
            }
            list1.next = list1.next.val < list2.val ? list1.next : list2;
            mergeTwoLists(list1next, list2);
        }
//        else { // если list1.val > list2.val
            ListNode list2next = list2.next;
            if (list2next == null) {
                list2.next = list1;
                return list2.next;
            }
            list2.next = list2.next.val < list1.val ? list2.next : list1;
        return     mergeTwoLists(list1, list2next);
//        }
//        return list1;
    }

    public static void main(String[] args) {

        ListNode four_null_1 = new ListNode(5, null);
//        ListNode two_four_1 = new ListNode(2, four_null_1);
//        ListNode one_two_1 = new ListNode(1, two_four_1);

        ListNode four_null_2 = new ListNode(4, null);
        ListNode three_four_2 = new ListNode(3, four_null_2);
        ListNode one_three_2 = new ListNode(1, three_four_2);

        System.out.println( mergeTwoLists(four_null_1,one_three_2).val);
    }
}
