package com.cskaoyan.hot100;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author duanqiaoyanyu
 * @date 2022/12/30 15:44
 */
public class Problem239 {

    class Solution {
        public int[] maxSlidingWindow(int[] nums, int k) {
            int length = nums.length;
            int[] result = new int[length - k + 1];
            Deque<Integer> deque = new ArrayDeque<>();

            for (int i = 0, j = 0; i < length; i++) {
                // 防止队头滑出窗口
                if (!deque.isEmpty() && i - k + 1 > deque.peekFirst()) {
                    deque.pollFirst();
                }

                // 一直弹出不必要的值, 保证队列的单调性, 这里是单调递减
                while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
                    deque.pollLast();
                }

                // 一直迭代
                deque.offerLast(i);
                if (i >= k - 1) {
                    // 由于是单调递减, 所以队首肯定就是最大值
                    result[j++] = nums[deque.peekFirst()];
                }
            }

            return result;
        }
    }
}
