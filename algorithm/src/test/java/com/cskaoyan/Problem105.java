package com.cskaoyan;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author duanqiaoyanyu
 * @date 2023/1/7 9:34
 */
public class Problem105 {

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
        public TreeNode buildTree(int[] preorder, int[] inorder) {
            // preorder root left right [3,9,20,15,7]
            // inorder left root right  [9,3,15,20,7]

            if (null == preorder || 0 == preorder.length || null == inorder || 0 == inorder.length) {
                return null;
            }

            int rootVal = preorder[0];
            TreeNode root = new TreeNode(rootVal);
            int inorderIndex = 0;
            for (int i = 0; i < inorder.length; i++) {
                if (rootVal == inorder[i]) {
                    inorderIndex = i;
                    break;
                }
            }

            int[] leftInorder = Arrays.copyOfRange(inorder, 0, inorderIndex);
            int[] rightInorder = null;
            if (inorderIndex + 1 < inorder.length) {
                rightInorder = Arrays.copyOfRange(inorder, inorderIndex + 1, inorder.length);
            }

            int[] leftPreorder = Arrays.copyOfRange(preorder, 1, leftInorder.length + 1);
            int[] rightPreorder = null;
            if (leftInorder.length + 1 < preorder.length) {
                rightPreorder = Arrays.copyOfRange(preorder, leftInorder.length + 1, preorder.length);
            }

            TreeNode left = buildTree(leftPreorder, leftInorder);
            TreeNode right = buildTree(rightPreorder, rightInorder);
            root.left = left;
            root.right = right;

            return root;
        }

    }

    @Test
    public void test1() {
        Solution solution = new Solution();
        int[] pre = new int[] {3,9,20,15,7};
        int[] in = new int[] {9,3,15,20,7};

        TreeNode node = solution.buildTree(pre, in);
        System.out.println(node);
    }
}
