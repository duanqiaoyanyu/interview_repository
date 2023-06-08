package com.cskaoyan;

import java.util.ArrayList;
import java.util.List;

/**
 * @author duanqiaoyanyu
 * @date 2023/6/8 11:46
 */
public class Problem39 {

    class Solution {
        public List<List<Integer>> combinationSum(int[] candidates, int target) {
            List<List<Integer>> result = new ArrayList<>();
            dfs(candidates, target, 0, new ArrayList<>(), result);
            return result;
        }

        private void dfs(int[] candidates, int target, int i, List<Integer> combination, List<List<Integer>> result) {
            if (target == 0) {
                result.add(new ArrayList<>(combination));
                return;
            }
            if (target < 0) {
                return;
            }
            for (int j = i; j < candidates.length; j++) {
                combination.add(candidates[j]);
                // 每个数字可以使用无数次，所以递归还可以从当前数字开始
                dfs(candidates, target - candidates[j], j, combination, result);
                combination.remove(combination.size() - 1);
            }
        }
    }
}
