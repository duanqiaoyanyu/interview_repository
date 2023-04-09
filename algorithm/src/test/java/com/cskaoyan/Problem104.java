package com.cskaoyan;

import java.util.Objects;

/**
 * @author duanqiaoyanyu
 * @date 2023/4/4 19:38
 */
public class Problem104 {


    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    class Solution {
        public int maxDepth(TreeNode root) {
            if (Objects.isNull(root)) {
                return 0;
            }

            int leftMax = maxDepth(root.left);
            int maxRight = maxDepth(root.right);

            return leftMax >= maxRight ? leftMax + 1 : maxRight + 1;
        }
    }
}
