package com.cskaoyan.hot100;

/**
 * @author duanqiaoyanyu
 * @date 2023/4/3 20:00
 */
public class Problem70 {

    class Solution {
        public int climbStairs(int n) {
            int result = 0;
            int a = 0;
            int b = 1;
            for (int i = 0; i < n; i++) {
                result = a + b;
                a = b;
                b = result;
            }

            return result;
        }
    }
}
