package com.cskaoyan;

import org.junit.Test;

/**
 * @author duanqiaoyanyu
 * @date 2023/4/6 10:18
 */
public class Problem160 {


    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public class Solution {
        public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
            if (headA == null || headB == null) {
                return null;
            }

            ListNode pa = headA, pb = headB;

            while (pa != null) {
                while (pb != null) {
                    if (pa == pb) {
                        return pa;
                    }

                    pb = pb.next;
                }

                pa = pa.next;
                pb = headB;
            }

            return null;
        }
    }

    @Test
    public void test1() {
        ListNode node1 = new ListNode(8);

        ListNode node2 = new ListNode(4);
        ListNode node3 = new ListNode(1);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);

        node2.next = node3;
        node3.next = node1;
        node1.next = node4;
        node4.next = node5;

        Solution solution = new Solution();
        ListNode node = solution.getIntersectionNode(node1, node2);
        System.out.println(node);
    }
}
