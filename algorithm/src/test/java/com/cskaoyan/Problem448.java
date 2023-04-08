package com.cskaoyan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author duanqiaoyanyu
 * @date 2023/4/8 10:14
 */
public class Problem448 {

    class Solution {
        public List<Integer> findDisappearedNumbers(int[] nums) {
            Map<Integer, Boolean> map = new HashMap<>();
            for (int num : nums) {
                map.put(num, true);
            }

            List<Integer> list = new ArrayList<>();
            for (int i = 1; i <= nums.length; i++) {
                if (!map.containsKey(i)) {
                    list.add(i);
                }
            }

            return list;
        }
    }
}
