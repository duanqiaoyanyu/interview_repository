package com.cskaoyan.hot100;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author duanqiaoyanyu
 * @date 2022/02/28 上午 10:10
 */
@SpringBootTest
public class Problem32 {

    class Solution {

        public class TreeNode {
            int val;
            TreeNode left;
            TreeNode right;

            TreeNode(int x) {
                val = x;
            }
        }

        public List<List<Integer>> levelOrder(TreeNode root) {
            if (root == null) {
                return new ArrayList<>();
            }

            List<List<Integer>> result = new ArrayList<>();

            List<Integer> level1 = new ArrayList<>(1);
            level1.add(root.val);
            result.add(level1);

            int level = 2;
            boolean hasChildren = true;
            List<TreeNode> lastLevelNodes = new ArrayList<>();
            lastLevelNodes.add(root);

            while (hasChildren) {
                List<Integer> levelVals = new ArrayList<>();
                List<TreeNode> currentLevelNodes = new ArrayList<>();
                // 奇数行 从左到右
                if (level % 2 == 1) {
                    int size = lastLevelNodes.size();
                    for (int i = 0; i < size; i++) {
                        TreeNode treeNode = lastLevelNodes.get(i);
                        TreeNode leftChild = treeNode.left;
                        if (leftChild != null) {
                            levelVals.add(leftChild.val);
                            currentLevelNodes.add(leftChild);
                        }

                        TreeNode rightChild = treeNode.right;
                        if (rightChild != null) {
                            levelVals.add(rightChild.val);
                            currentLevelNodes.add(rightChild);
                        }
                    }
                } else {
                    // 偶数行 从右到左
                    int size = lastLevelNodes.size();
                    for (int i = size - 1; i >= 0; i--) {
                        TreeNode treeNode = lastLevelNodes.get(i);

                        TreeNode rightChild = treeNode.right;
                        if (rightChild != null) {
                            levelVals.add(rightChild.val);
                            currentLevelNodes.add(0, rightChild);
                        }

                        TreeNode leftChild = treeNode.left;
                        if (leftChild != null) {
                            levelVals.add(leftChild.val);
                            currentLevelNodes.add(0, leftChild);
                        }
                    }
                }

                if (currentLevelNodes.size() == 0) {
                    hasChildren = false;
                } else {
                    result.add(levelVals);
                }

                level++;
                lastLevelNodes = currentLevelNodes;
            }

            return result;
        }
    }


    // 按照 "之" 字形进行打印二叉树 第一层从左到右 第二层从右到左 按此规律直到遍历完二叉树所有层级

    // 1. 奇数层-从左到右进行遍历  偶数层-从右到左进行遍历
    // 2. 遍历一行得到一个数组 然后加入到最终结果的大数组中
    // 3. 其中 需要引入一个层级变量表示当前遍历到了第几层。还要有一个迭代数组 保存的是上一层级中的所有结点, 保存的方式是
    // 将上一层级中的节点从左到右 依次保存到数组的第 0 个、第 1 个位置...
    // 4. 遍历的过程中先判断奇偶,如果奇数则从左到右遍历迭代数组, 遍历数组中的每一个元素时, 先访问元素的左孩子然后再访问右孩子, 将对应的元素的val
    // 添加进每一层的结果数组中, 并且每次遍历会新建一个当前层级所有节点的列表, 当前节点列表将访问到的孩子添加进数组中
    // 当是偶数层也就是从右到左遍历迭代数组, 就是从迭代数组的最后一个元素 size - 1 往下表为 0 的方向进行遍历, 遍历每一个元素的时候,
    // 先访问元素的右孩子, 然后再访问左孩子。将对应元素的val添加进每一层的结果数组中, 但是额外注意的是把访问到的孩子添加进当前层级列表的时候
    // 需要把他添加到数组的第 0 个位置, 这样才能保证当前数组的顺序是从左到右的
    // 5. 遍历每一层结束后 需要将层级数进行迭代 然后需要把当前数组赋值给迭代数组。 并且当前数组为空时表示已经遍历到了最后一层 需要跳出循环
}
