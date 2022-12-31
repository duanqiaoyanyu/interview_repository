package com.cskaoyan;

/**
 * @author duanqiaoyanyu
 * @date 2022/12/26 18:53
 */
public class Problem617 {
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
        public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
            if (root1 != null && root2 != null) {
                root1.val += root2.val;
                TreeNode newLeft = mergeTrees(root1.left, root2.left);
                TreeNode newRight = mergeTrees(root1.right, root2.right);
                root1.left = newLeft;
                root1.right = newRight;

                return root1;
            } else if (root1 != null) {
                return root1;
            } else if (root2 != null) {
                return root2;
            } else {
                return null;
            }
        }
    }
}
