package com.cskaoyan;

/**
 * @author duanqiaoyanyu
 * @date 2023/4/5 18:43
 */
public class Problem136 {

    class Solution {
        public int singleNumber(int[] nums) {
            int result = 0;
            for (int num : nums) {
                result ^= num;
            }

            return result;
        }
    }
}
