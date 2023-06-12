package com.cskaoyan.hot100;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author duanqiaoyanyu
 * @date 2023/6/5 15:33
 */
public class Problem17 {

    class Solution {
        public List<String> letterCombinations(String digits) {
            if (digits == null || digits.length() == 0) {
                return Collections.emptyList();
            }

            List<String> result = new ArrayList<>();
            String[] alphabets = new String[]{"abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

            char[] chars = digits.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                String s = alphabets[chars[i] - '2'];
                if (result.isEmpty()) {
                    for (int j = 0; j < s.length(); j++) {
                        result.add(String.valueOf(s.charAt(j)));
                    }
                } else {
                    List<String> temp = new ArrayList<>();
                    for (int j = 0; j < s.length(); j++) {
                        for (String s1 : result) {
                            temp.add(s1 + s.charAt(j));
                        }
                    }
                    result = temp;
                }
            }

            return result;
        }
    }
}
