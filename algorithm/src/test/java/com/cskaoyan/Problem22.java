package com.cskaoyan;

import java.util.ArrayList;
import java.util.List;

/**
 * @author duanqiaoyanyu
 * @date 2023/6/6 10:02
 */
public class Problem22 {

    class Solution {
        public List<String> generateParenthesis(int n) {
            List<String> result = new ArrayList<>();
            dfs(n, 0, 0, "", result);
            return result;
        }

        void dfs(int n, int left, int right, String s, List<String> result) {
            if (left == n && right == n) {
                result.add(s);
                return;
            }

            if (left < n) {
                dfs(n, left + 1, right, s + "(", result);
            }

            if (right < left) {
                dfs(n, left, right + 1, s + ")", result);
            }
        }
    }
}
