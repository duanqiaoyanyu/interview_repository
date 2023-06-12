package com.cskaoyan.hot100;

import java.util.HashMap;
import java.util.Map;

/**
 * @author duanqiaoyanyu
 * @date 2023/4/10 18:29
 */
public class Problem3 {

    class Solution {
        public int lengthOfLongestSubstring(String s) {
            if (s == null || s.length() == 0) {
                return 0;
            }

            int result = 0;
            char[] chars = s.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                Map<Character, Boolean> map = new HashMap<>();
                int j;
                for (j = i; j < chars.length; j++) {
                    if (map.containsKey(chars[j])) {
                        break;
                    }

                    map.put(chars[j], true);
                }

                result = Math.max(result, j - i);
            }

            return result;
        }
    }
}
