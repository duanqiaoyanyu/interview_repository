package com.cskaoyan;

import org.junit.Test;

public class Problem283 {

    class Solution {
        public void moveZeroes(int[] nums) {
            if (nums == null || nums.length == 0) {
                return;
            }

            int length = nums.length;
            int zeroCount = 0;
            for (int i = 0; i < length - zeroCount; ) {
                if (nums[i] == 0) {
                    for (int j = i; j < length - 1 - zeroCount; j++) {
                        nums[j] = nums[j + 1];
                    }

                    nums[length - 1 - zeroCount] = 0;
                    zeroCount++;
                } else {
                    i++;
                }
            }
        }
    }

    @Test
    public void test1() {
        Solution solution = new Solution();

        int[] nums = {0, 1, 0, 3, 12};
        solution.moveZeroes(nums);

        System.out.println(nums);
    }
}
