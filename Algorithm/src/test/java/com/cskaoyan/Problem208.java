package com.cskaoyan;

import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author duanqiaoyanyu
 * @date 2022/02/26 下午 04:00
 */
@SpringBootTest
public class Problem208 {

    /**
     * 输入
     * ["Trie", "insert", "search", "search", "startsWith", "insert", "search"]
     * [[], ["apple"], ["apple"], ["app"], ["app"], ["app"], ["app"]]
     * 输出
     * [null, null, true, false, true, null, true]
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/implement-trie-prefix-tree
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */

    class Trie {
        // count 表示以当前单词结尾的单词数量
        int count = 0;
        private char value;
        private Trie[] children = new Trie[26];

        public Trie() {

        }

        public void insert(String word) {
            char[] chars = word.toCharArray();
            int length = chars.length;
            Trie current = this;
            Trie child;
            for (int i = 0; i < length; i++) {
                int index = chars[i] - 'a';
                child = current.children[index];
                if (child == null) {
                    child = new Trie();
                    child.value = chars[i];
                    current.children[index] = child;
                }

                current = child;
            }

            current.count = 1;
        }

        public boolean search(String word) {
            char[] chars = word.toCharArray();
            int length = chars.length;
            Trie current = this;
            Trie child;
            for (int i = 0; i < length; i++) {
                int index = chars[i] - 'a';
                child = current.children[index];
                if (child == null) {
                    return false;
                }

                current = child;
            }

            return current.count == 1;
        }

        public boolean startsWith(String prefix) {
            char[] chars = prefix.toCharArray();
            int length = chars.length;
            Trie current = this;
            Trie child;
            for (int i = 0; i < length; i++) {
                int index = chars[i] - 'a';
                child = current.children[index];
                if (child == null) {
                    return false;
                }

                current = child;
            }

            return true;
        }

/**
 *
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */


// ------------------插入方法---------------
// 1. 首先把 word 变成字符数组。前缀树的原理就是根节点不保存元素, 然后下面的孩子节点都保存一个字符
// 然后从根节点开始的路径所组成的字符就是对应的字符串了。 所以一个相当于需要在根节点的孩子保存word的第一个字符,
// 然后保存了第一个字符的节点的孩子保存了第二个字符。这里通过巧妙的设计把每一个节点的孩子们用一个大小为26的数组表示,
// 下标0-25 分别代表着 'a'-'z'.然后如果那一层对应的元素没有就需要新增。直到将word数组遍历完成
    }
}
