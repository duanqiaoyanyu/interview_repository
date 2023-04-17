package com.cskaoyan;

/**
 * @author duanqiaoyanyu
 * @date 2023/4/9 17:35
 */
public class Problem2 {


    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    class Solution {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            ListNode t1 = l1, t2 = l2, temp = null, tail = new ListNode(0), head = tail;
            boolean moreThanOrEqualTen = false;
            while (t1 != null || t2 != null) {
                int num;
                boolean last;
                if (t1 != null && t2 != null) {
                    if (moreThanOrEqualTen) {
                        num = t1.val + t2.val + 1;
                    } else {
                        num = t1.val + t2.val;
                    }

                    last = t1.next == null && t2.next == null;
                    t1 = t1.next;
                    t2 = t2.next;
                } else if (t1 != null) {
                    if (moreThanOrEqualTen) {
                        num = t1.val + 1;
                    } else {
                        num = t1.val;
                    }

                    last = t1.next == null;
                    t1 = t1.next;
                } else {
                    if (moreThanOrEqualTen) {
                        num = t2.val + 1;
                    } else {
                        num = t2.val;
                    }

                    last = t2.next == null;
                    t2 = t2.next;
                }

                moreThanOrEqualTen = num >= 10;
                temp = moreThanOrEqualTen ? new ListNode(num - 10) : new ListNode(num);
                if (last && moreThanOrEqualTen) {
                    ListNode lastNode = new ListNode(1);
                    temp.next = lastNode;
                    tail.next = temp;
                    tail = lastNode;
                } else {
                    tail.next = temp;
                    tail = temp;
                }

            }

            return head.next;
        }
    }
}
