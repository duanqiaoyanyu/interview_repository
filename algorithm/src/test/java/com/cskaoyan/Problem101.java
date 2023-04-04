package com.cskaoyan;

import java.util.Objects;

/**
 * @author duanqiaoyanyu
 * @date 2023/4/4 18:38
 */
public class Problem101 {


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
        public boolean isSymmetric(TreeNode root) {
            if (Objects.isNull(root)) {
                return false;
            }

            return isSame(root.left, root.right);
        }

        private boolean isSame(TreeNode left, TreeNode right) {
            if (Objects.nonNull(left) && Objects.isNull(right)) {
                return false;
            }

            if (Objects.isNull(left) && Objects.nonNull(right)) {
                return false;
            }

            if (Objects.isNull(left)) {
                return true;
            }

            if (left.val != right.val) {
                return false;
            }

            return isSame(left.left, right.right) && isSame(left.right, right.left);
        }
    }
}
