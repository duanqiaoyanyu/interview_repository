package com.cskaoyan;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 *
 * {@link Map#compute(Object, BiFunction)}
 * 第一个参数是 key, 第二个参数是一个二元 Function
 * 其中逻辑就是用二元 Function, 传进去 key, oldValue 计算得到 new Value
 * 1. 如果 newValue 为空, 如果 oldValue 不为空, 或者 包含这个 key, 那么就删除这个 key, 返回 null
 * 2. 如果 newValue 为空, 并且不是 1 的情况, 就什么都不做, 返回 null
 * 3. 如果 newValue 不为空， 增加或者替换老的映射关系, 返回 newValue
 *
 */
public class Problem169 {

    class Solution {
        public int majorityElement(int[] nums) {
            int majorityCount = Math.floorDiv(nums.length, 2);

            Map<Integer, Integer> elementToCount = new HashMap<>();
            for (int num : nums) {
                elementToCount.compute(num, (key, oldValue) -> {
                    if (oldValue == null) {
                        return 1;
                    }

                    return oldValue + 1;
                });
            }

            for (Map.Entry<Integer, Integer> entry : elementToCount.entrySet()) {
                if (entry.getValue() > majorityCount) {
                    return entry.getKey();
                }
            }

            return 0;
        }
    }

    @Test
    public void test1() {
        Solution solution = new Solution();

        int[] nums = {3, 2, 3};
        solution.majorityElement(nums);
    }
}
