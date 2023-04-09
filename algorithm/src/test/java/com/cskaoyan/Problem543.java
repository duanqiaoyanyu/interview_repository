package com.cskaoyan;

import java.util.Objects;

/**
 * @author duanqiaoyanyu
 * @date 2023/4/8 11:09
 */
public class Problem543 {

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
        private int result;

        public int diameterOfBinaryTree(TreeNode root) {
            if (Objects.isNull(root)) {
                return 0;
            }

            getDepthAndCompute(root);

            return result;
        }

        private int getDepthAndCompute(TreeNode root) {
            if (Objects.isNull(root)) {
                return 0;
            }

            int leftDepth = getDepthAndCompute(root.left);
            int rightDepth = getDepthAndCompute(root.right);
            result = Math.max(result, leftDepth + rightDepth);
            return 1 + Math.max(leftDepth, rightDepth);
        }
    }
}
