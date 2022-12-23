package com.cskaoyan;

import java.util.HashSet;
import java.util.Set;

/**
 * @author duanqiaoyanyu
 * @date 2022/12/22 16:52
 */
public class Problem128 {
    class Solution {
        public int longestConsecutive(int[] nums) {
            int result = 0;

            Set<Integer> set = new HashSet<>(nums.length);
            for (int num : nums) {
                set.add(num);
            }

            for (int num : nums) {
                int length = 1;
                if (!set.contains(num - 1)) {
                    int next = num + 1;
                    while (set.contains(next++)) {
                        length++;
                    }
                }

                result = Math.max(result, length);
            }

            return result;
        }
    }
}
