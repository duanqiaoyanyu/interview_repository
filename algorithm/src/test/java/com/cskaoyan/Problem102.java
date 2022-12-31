package com.cskaoyan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author duanqiaoyanyu
 * @date 2022/12/31 16:24
 */
public class Problem102 {

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
        public List<List<Integer>> levelOrder(TreeNode root) {
            if (Objects.isNull(root)) {
                return Collections.emptyList();
            }

            List<List<Integer>> result = new ArrayList<>();

            List<TreeNode> currentLevel = new ArrayList<>();
            currentLevel.add(root);
            boolean notAllNull = true;
            while (notAllNull) {
                List<TreeNode> tempLevel = new ArrayList<>();
                List<Integer> levelResult = new ArrayList<>();
                for (TreeNode treeNode : currentLevel) {
                    levelResult.add(treeNode.val);

                    if (Objects.nonNull(treeNode.left)) {
                        tempLevel.add(treeNode.left);
                    }

                    if (Objects.nonNull(treeNode.right)) {
                        tempLevel.add(treeNode.right);
                    }
                }

                result.add(levelResult);
                currentLevel = tempLevel;
                notAllNull = currentLevel.stream()
                        .anyMatch(Objects::nonNull);
            }

            return result;
        }
    }
}
