package com.cskaoyan;

/**
 *
 * 32. 最长有效括号
 * 给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。
 *
 *
 * "()(())"
 *
 * @author duanqiaoyanyu
 * @date 2022/12/27 10:17
 */
public class Problem32H {

    /**
     * dp[i] 表示以 chars[i]结尾的最长有效长度
     */
    class Solution {
        public int longestValidParentheses(String s) {
            int result = 0;
            char[] chars = s.toCharArray();
            int[] dp = new int[s.length()];

            for (int i = 1; i < chars.length; i++) {
                if (chars[i] == ')') {
                    if (chars[i - 1] == '(') {
                        dp[i] = 2 + (i > 1 ? dp[i - 2] : 0);
                    } else {
                        int j = i - 1 - dp[i - 1];
                        if (j >=0 && chars[j] == '(') {
                            dp[i] = dp[i - 1] + 2 + (j > 0 ? dp[j - 1] : 0);
                        }
                    }

                    result = Math.max(result, dp[i]);
                }
            }

            return result;
        }
    }
}
