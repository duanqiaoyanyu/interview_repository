package com.cskaoyan.hot100;

/**
 * @author duanqiaoyanyu
 * @date 2023/4/11 18:22
 */
public class Problem5 {

    class Solution {
        public String longestPalindrome(String s) {
            if (s == null || s.length() == 0) {
                return null;
            }

            char[] chars = s.toCharArray();
            int length = s.length();
            boolean[][] dp = new boolean[length][length];
            int longest = 1, startIndex = 0;

            // j >= i
            for (int i = length - 2; i >= 0; i--) {
                for (int j = i; j < length; j++) {
                    if (j - i < 2) {
                        dp[i][j] = chars[i] == chars[j];
                    } else {
                        dp[i][j] = dp[i + 1][j - 1] && chars[i] == chars[j];
                    }

                    if (dp[i][j] && longest < j - i + 1) {
                        longest = j - i + 1;
                        startIndex = i;
                    }
                }
            }

            return s.substring(startIndex, startIndex + longest);
        }
    }
}
