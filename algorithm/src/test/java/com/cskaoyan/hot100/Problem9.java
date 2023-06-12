package com.cskaoyan.hot100;

/**
 * @author duanqiaoyanyu
 * @date 2023/4/3 15:54
 */
public class Problem9 {

    class Solution {
        public boolean isPalindrome(int x) {
            int t = Math.abs(x);
            int s = 0;
            while (t != 0) {
                s = 10 * s + t % 10;
                t /= 10;
            }

            return x == s;
        }
    }
}
