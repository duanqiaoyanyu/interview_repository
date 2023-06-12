package com.cskaoyan.hot100;

import java.util.HashMap;
import java.util.Map;

/**
 * @author duanqiaoyanyu
 * @date 2023/1/4 15:39
 */
public class Problem437 {
      public class TreeNode {
          int val;
          TreeNode left;
          TreeNode right;
          TreeNode() {}
          TreeNode(int val) { this.val = val; }
          TreeNode(int val, TreeNode left, TreeNode right) {
              this.val = val;
              this.left = left;
              this.right = right;
          }
      }

    class Solution {
        /**
         * key: 前缀的和的值
         * value: 路径和为 targetSum 的数量
         */
        Map<Long, Long> preSumToPathSumNumMap = new HashMap<>();
        int targetSum;
        public int pathSum(TreeNode root, int targetSum) {
            this.targetSum = targetSum;
            preSumToPathSumNumMap.put(0L, 1L);
            return dfs(root, 0L).intValue();
        }

        private Long dfs(TreeNode root, Long preSum) {
            if (root == null) {
                return 0L;
            }

            preSum += root.val;
            Long pathSumNum = preSumToPathSumNumMap.getOrDefault(preSum - targetSum, 0L);
            preSumToPathSumNumMap.merge(preSum, 1L, Long::sum);

            pathSumNum += dfs(root.left, preSum);
            pathSumNum += dfs(root.right, preSum);

            preSumToPathSumNumMap.merge(preSum, -1L, Long::sum);

            return pathSumNum;
        }
    }
}
