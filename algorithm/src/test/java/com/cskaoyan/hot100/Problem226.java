package com.cskaoyan.hot100;

import java.util.Objects;

/**
 * @author duanqiaoyanyu
 * @date 2023/4/7 19:00
 */
public class Problem226 {

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
        public TreeNode invertTree(TreeNode root) {
            if (Objects.isNull(root)) {
                return null;
            }

            TreeNode right = invertTree(root.left);
            TreeNode left = invertTree(root.right);

            root.right = right;
            root.left = left;

            return root;
        }
    }
}
