package com.cskaoyan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author duanqiaoyanyu
 * @date 2023/4/4 18:27
 */
public class Problem94 {


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
        public List<Integer> inorderTraversal(TreeNode root) {
            if (Objects.isNull(root)) {
                return Collections.emptyList();
            }

            List<Integer> result = new ArrayList<>();

            TreeNode left = root.left;
            List<Integer> leftResult = inorderTraversal(left);
            if (leftResult.size() != 0) {
                result.addAll(leftResult);
            }

            result.add(root.val);

            TreeNode right = root.right;
            List<Integer> rightResult = inorderTraversal(right);
            if (rightResult.size() != 0) {
                result.addAll(rightResult);
            }

            return result;
        }
    }
}
