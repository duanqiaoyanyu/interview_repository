package com.cskaoyan.hot100;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author duanqiaoyanyu
 * @date 2023/1/1 17:06
 */
public class Problem301 {

    class Solution {
        public List<String> removeInvalidParentheses(String s) {
            Set<String> result = new HashSet<>();

            int needDelLeft = 0;
            int needDelRight = 0;
            char[] chars = s.toCharArray();
            for (char aChar : chars) {
                if (aChar == '(') {
                    needDelLeft++;
                }

                if (aChar == ')') {
                    if (needDelLeft > 0) {
                        needDelLeft--;
                    } else {
                        needDelRight++;
                    }
                }
            }

            recursive(s, 0, needDelLeft, needDelRight, 0, 0, "", result);

            return new ArrayList<>(result);
        }

        private void recursive(String s, int i, int needDelLeft, int needDelRight, int targetLeft, int targetRight, String target, Set<String> result) {
            int length = s.length();
            if (i == length) {
                if (needDelLeft == 0 && needDelRight == 0) {
                    result.add(target);
                }

                return;
            }

            if (length - i < needDelLeft + needDelRight || targetLeft < targetRight) {
                return;
            }

            char c = s.charAt(i);
            if (c == '(' && needDelLeft > 0) {
                recursive(s, i + 1, needDelLeft - 1, needDelRight, targetLeft, targetRight, target, result);
            }

            if (c == ')' && needDelRight > 0) {
                recursive(s, i + 1, needDelLeft, needDelRight - 1, targetLeft, targetRight, target, result);
            }

            int targetLeftIncrement = c == '(' ? 1 : 0;
            int targetRightIncrement = c == ')' ? 1 : 0;
            recursive(s, i + 1, needDelLeft, needDelRight, targetLeft + targetLeftIncrement, targetRight + targetRightIncrement,
                    target + c, result);
        }
    }
}
