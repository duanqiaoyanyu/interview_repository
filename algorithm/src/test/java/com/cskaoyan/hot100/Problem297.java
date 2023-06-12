package com.cskaoyan.hot100;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 这一题我理解错题目的意思了, 我以为必须按照层级遍历二叉树的方式序列化为字符串. 原来题目的意思只是说能序列化为串和反序列化为树了
 * 至于序列化为什么样式的串, 题目没有要求。我按层级的做法内存会超。不过还是把自己做的给记录下来
 *
 * @author duanqiaoyanyu
 * @date 2022/12/29 10:18
 */
public class Problem297 {

      public class TreeNode {
          int val;
          TreeNode left;
          TreeNode right;
          TreeNode(int x) { val = x; }
      }

    public class CodecWrong {

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            StringBuilder result = new StringBuilder("[");
            boolean rootNotNull = Objects.nonNull(root);
            if (rootNotNull) {
                result.append(root.val).append(",");
            }

            List<TreeNode> currentLevel = new ArrayList<>();
            currentLevel.add(root);
            boolean anyNotNull = currentLevel.stream()
                    .anyMatch(Objects::nonNull);
            while (anyNotNull) {
                List<TreeNode> tempLevel = new ArrayList<>();
                for (int i = 0; i < currentLevel.size(); i++) {
                    TreeNode treeNode = currentLevel.get(i);
                    if (Objects.nonNull(treeNode)) {
                        TreeNode left = treeNode.left;
                        if (Objects.nonNull(left)) {
                            result.append(left.val).append(",");
                        } else {
                            result.append("null").append(",");
                        }

                        TreeNode right = treeNode.right;
                        if (Objects.nonNull(right)) {
                            result.append(right.val).append(",");
                        } else {
                            result.append("null").append(",");
                        }

                        tempLevel.add(left);
                        tempLevel.add(right);
                    } else {
                        result.append("null").append(",").append("null").append(",");
                        tempLevel.add(null);
                        tempLevel.add(null);
                    }
                }

                currentLevel = tempLevel;
                anyNotNull = currentLevel.stream()
                        .anyMatch(Objects::nonNull);
            }

            // 删除最后一个多余的 ','
            if (result.charAt(result.length() - 1) == ',') {
                result.deleteCharAt(result.length() - 1);
            }

            result.append("]");

            return result.toString();
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            data = data.replace("[", "").replace("]", "");
            if ("".equals(data)) {
                return null;
            }

            String[] vals = data.split(",");
            List<TreeNode> nodes = new ArrayList<>();
            for (int i = 0; i < vals.length; i++) {
                String val = vals[i];
                TreeNode node = null;
                if (!"null".equals(val)) {
                    node = new TreeNode(Integer.valueOf(val));
                }

                nodes.add(node);
            }

            for (int i = 0; i < nodes.size(); i++) {
                TreeNode node = nodes.get(i);
                if (Objects.nonNull(node)) {
                    int leftIndex = 2 * i + 1;
                    if (leftIndex < nodes.size()) {
                        node.left = nodes.get(leftIndex);
                    }

                    int rightIndex = 2 * i + 2;
                    if (rightIndex < nodes.size()) {
                        node.right = nodes.get(rightIndex);
                    }
                }
            }

            return nodes.get(0);
        }
    }

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));


    public class Codec {
          private static final String NULL = "#";
          private static final String SEPARATE = ",";

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            if (Objects.isNull(root)) {
                return "";
            }

            StringBuilder builder = new StringBuilder();
            preOrder(root, builder);

            return builder.toString();
        }

        private void preOrder(TreeNode root, StringBuilder builder) {
            if (Objects.isNull(root)) {
                builder.append(NULL).append(SEPARATE);
                return;
            }

            builder.append(root.val).append(SEPARATE);
            preOrder(root.left, builder);
            preOrder(root.right, builder);
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            if (data == null || "".equals(data)) {
                return null;
            }

            String[] split = data.split(SEPARATE);
            List<Integer> vals = new ArrayList<>();
            for (String s : split) {
                if (s.equals(NULL)) {
                    vals.add(null);
                } else {
                    vals.add(Integer.valueOf(s));
                }
            }

            return deserialize(vals);
        }

        private TreeNode deserialize(List<Integer> vals) {
            Integer val = vals.remove(0);
            if (Objects.isNull(val)) {
                return null;
            }

            TreeNode root = new TreeNode(val);
            root.left = deserialize(vals);
            root.right = deserialize(vals);

            return root;
        }
    }
}
