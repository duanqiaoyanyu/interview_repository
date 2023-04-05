package com.cskaoyan;

import java.util.Objects;

/**
 * @author duanqiaoyanyu
 * @date 2023/4/5 18:53
 */
public class Problem141 {


    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public class Solution {
        public boolean hasCycle(ListNode head) {
            if (Objects.isNull(head)) {
                return false;
            }

            ListNode fast = head, slow = head;

            while (fast != null && fast.next != null) {
                fast = fast.next.next;
                slow = slow.next;

                if (fast == slow) {
                    return true;
                }
            }

            return false;
        }
    }
}
