package ru.progwards.smartglobus;

public class SolutionMergeTwoLists {
    private ListNode startNode;
    private boolean firstStep;

    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {

//        if (!firstStep) {
//            startNode = list1.val <= list2.val ? list1 : list2;
//            firstStep = true;
//        }
        if (list1 == null) return list2;
        if (list2 == null) return list1;
        if (list1.val <= list2.val) {
            list1.next = mergeTwoLists(list1.next, list2);
            return list1;
//            ListNode list1next = list1.next;
//            if (list1.next == null) {
//                list1.next = list2;
//                return startNode;
//            }
//
//            list1.next = list1.next.val <= list2.val ? list1.next : list2;
//            mergeTwoLists(list1next, list2);
        } else { // если list1.val > list2.val
            list2.next = mergeTwoLists(list1, list2.next);
            return list2;
//            ListNode list2next = list2.next;
//            if (list2.next == null) {
//                list2.next = list1;
//                return startNode;
//            }
//
//            list2.next = list2.next.val < list1.val ? list2.next : list1;
//            mergeTwoLists(list1, list2next);
//        }
//        return startNode;
        }
    }

    public static ListNode reverseList(ListNode head) {
//        if (head==null)return null;
        int val = head.val;
        if (head.next == null) return new ListNode(head.val, reverseList(head));
//        ListNode curr = head;
        ListNode nxt = head.next;
        ListNode rev = new ListNode(nxt.val, head);
//        int tmp = head.next.val;
        reverseList(head.next);
//        nxt.next = curr;
//
//        if (head.next.next == null) return head.next;
//
//        reverseList(head.next);
//int y = move(head.val);
//
        return rev;
    }


    public static void main(String[] args) {
        ListNode five_null_1 = new ListNode(5, null);
        ListNode four_null_1 = new ListNode(4, five_null_1);
        ListNode two_four_1 = new ListNode(3, four_null_1);
        ListNode one_two_1 = new ListNode(2, two_four_1);

//        ListNode four_null_2 = new ListNode(4, null);
//        ListNode three_four_2 = new ListNode(3, four_null_2);
//        ListNode one_three_2 = new ListNode(1, three_four_2);
        System.out.println(reverseList(one_two_1));
//        ListNode out = mergeTwoLists(one_two_1, one_three_2);
//        System.out.println(out);
//        System.out.println(out.next.val);
//        System.out.println(out.next.next.val);
//        System.out.println(out.next.next.next.val);
//        System.out.println(out.next.next.next.next.val);
//        System.out.println(out.next.next.next.next.next.val);


    }
}

// Definition for singly-linked list.
class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

//    @Override
//    public String toString() {
//
//        return val + "\nval=" + val + " next=" + next + "\n";
//    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}