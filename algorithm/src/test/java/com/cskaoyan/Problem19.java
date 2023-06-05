package com.cskaoyan;

/**
 * @author duanqiaoyanyu
 * @date 2023/6/5 17:25
 */
public class Problem19 {
      public class ListNode {
          int val;
          ListNode next;
          ListNode() {}
          ListNode(int val) { this.val = val; }
          ListNode(int val, ListNode next) { this.val = val; this.next = next; }
      }
    class Solution {
        public ListNode removeNthFromEnd(ListNode head, int n) {
            ListNode dummy = new ListNode(0, head);
            ListNode first = head;
            ListNode second = dummy;
            for (int i = 0; i < n; ++i) {
                first = first.next;
            }
            while (first != null) {
                first = first.next;
                second = second.next;
            }
            second.next = second.next.next;
            ListNode ans = dummy.next;
            return ans;
        }
    }

    // second 指向第 1 个节点, first 指向第 n + 2 个节点
    // second 指向倒数第 n + 2 个节点                     first 指向倒数第一个节点
    // second 指向倒数第 n + 1 个节点                     first 指向 null
    // 删除倒数第 n 个节点, 所以倒数第 n + 1 的节点指向下一个节点就好
    // 返回原始链表就好
}
