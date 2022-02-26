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
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/implement-trie-prefix-tree
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */

    class Trie {
        private char value;
        private Trie[] children = new Trie[26];

        public Trie() {

        }

        public void insert(String word) {
            char[] chars = word.toCharArray();
            int length = chars.length;
            for (int i = 0; i < length; i++) {
                int index = chars[i] - 'a';
                Trie child = children[index];
                if (child == null) {
                    children[index].value = chars[i];
                }

                continue;
            }
        }

        public boolean search(String word) {

        }

        public boolean startsWith(String prefix) {

        }
    }

/**
 *
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
}
