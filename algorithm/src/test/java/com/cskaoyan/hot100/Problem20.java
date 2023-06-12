package com.cskaoyan.hot100;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author duanqiaoyanyu
 * @date 2023/4/3 15:26
 */
public class Problem20 {

    class Solution {
        public boolean isValid(String s) {
            char[] chars = s.toCharArray();
            Deque<Character> deque = new ArrayDeque<>();

            for (char aChar : chars) {
                if (aChar == '[' || aChar == '{' || aChar == '(') {
                    deque.push(aChar);
                } else if (deque.isEmpty() || !isMatch(deque.pop(), aChar)) {
                    return false;
                }
            }

            return deque.isEmpty();
        }

        private boolean isMatch(Character left, char right) {
            if (left == '(') {
                return right == ')';
            } else if (left == '{') {
                return right == '}';
            } else {
                return right == ']';
            }
        }
    }
}
