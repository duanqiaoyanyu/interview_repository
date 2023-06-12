package com.cskaoyan.hot100;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author duanqiaoyanyu
 * @date 2023/1/4 10:20
 */
public class Problem234 {
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

        public boolean isPalindrome(ListNode head) {
            ListNode headTemp = head;
            List<ListNode> nodes = new ArrayList<>();

            while (headTemp != null) {
                nodes.add(headTemp);
                headTemp = headTemp.next;
            }

            boolean palindrome = true;
            for (int i = nodes.size() - 1; i >= 0; i--) {
                ListNode node = nodes.get(i);
                if (head.val != node.val) {
                    palindrome = false;
                    break;
                }

                head = head.next;
            }

            return palindrome;
        }
    }

    @Test
    public void test1() {
        ListNode tail = new ListNode(1);
        ListNode node2 = new ListNode(2, tail);
        ListNode node1 = new ListNode(2, node2);
        ListNode head = new ListNode(2, node1);

        Solution solution = new Solution();
        solution.isPalindrome(head);
    }
}
