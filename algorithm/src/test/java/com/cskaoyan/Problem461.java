package com.cskaoyan;

/**
 * @author duanqiaoyanyu
 * @date 2023/4/8 10:31
 */
public class Problem461 {

    class Solution {
        public int hammingDistance(int x, int y) {
            int z = x ^ y;
            int result = 0;
            while (z != 0) {
                int remainder = z % 2;
                if (remainder == 1) {
                    result++;
                }

                z /= 2;
            }

            return result;
        }
    }
}
