package com.cskaoyan.hot100;

import org.junit.Test;

import java.util.Objects;

/**
 * @author duanqiaoyanyu
 * @date 2023/4/7 14:21
 */
public class Problem206 {

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

    // A -> B -> C -> D
    class Solution {
        public ListNode reverseList(ListNode head) {
            if (Objects.isNull(head)) {
                return null;
            }

            ListNode phead = head, temp = phead.next;

            while (temp != null) {
                phead.next = temp.next;
                temp.next = head;
                head = temp;
                temp = phead.next;
            }

            return head;
        }
    }

    @Test
    public void test1() {
        ListNode node5 = new ListNode(5);
        ListNode node4 = new ListNode(4, node5);
        ListNode node3 = new ListNode(3, node4);
        ListNode node2 = new ListNode(2, node3);
        ListNode node1 = new ListNode(1, node2);

        Solution solution = new Solution();
        ListNode reversed = solution.reverseList(node1);
        System.out.println(reversed);
    }
}
