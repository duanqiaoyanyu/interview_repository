package com.cskaoyan.hot100;

import java.util.ArrayList;
import java.util.List;

/**
 * @author duanqiaoyanyu
 * @date 2023/6/12 10:44
 */
public class Problem46 {

    class Solution {
        public List<List<Integer>> permute(int[] nums) {
            List<List<Integer>> result = new ArrayList<>();
            List<Integer> path = new ArrayList<>();
            boolean[] used = new boolean[nums.length];
            dfs(nums, 0, path, used, result);

            return result;
        }

        private void dfs(int[] nums, int i, List<Integer> path, boolean[] used, List<List<Integer>> result) {
            if (i == nums.length) {
                result.add(new ArrayList<>(path));
                return;
            }

            for (int j = 0; j < nums.length; j++) {
                if (!used[j]) {
                    path.add(nums[j]);
                    used[j] = true;
                    // 递归
                    dfs(nums, i + 1, path, used, result);
                    // 回溯
                    path.remove(path.size() - 1);
                    // 回溯
                    used[j] = false;
                }
            }
        }
    }
}
