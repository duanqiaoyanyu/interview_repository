package com.cskaoyan;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author duanqiaoyanyu
 * @date 2023/4/13 18:35
 */
public class Problem15 {

    class Solution {
        public List<List<Integer>> threeSum(int[] nums) {
            if (nums == null || nums.length == 0) {
                return Collections.emptyList();
            }

            Arrays.sort(nums);
            int length = nums.length;
            List<List<Integer>> result = new ArrayList<>();
            for (int i = 0; i < length - 2 && nums[i] <= 0; i++) {
                if (i > 0 && nums[i] == nums[i - 1]) {
                    continue;
                }

                int j = i + 1, k = length - 1;
                while (j < k) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        result.add(Arrays.asList(nums[i], nums[j++], nums[k--]));

                        while (j < length && nums[j] == nums[j - 1]) {
                            j++;
                        }

                        while (k < length -2 && nums[k] == nums[k + 1]) {
                            k--;
                        }
                    } else if (nums[i] + nums[j] + nums[k] < 0) {
                        j++;
                    } else {
                        k--;
                    }
                }
            }

            return result;
        }
    }

    @Test
    public void test1() {
        Solution solution = new Solution();
        int[] nums = {-1, 0, 1, 2, -1, -4};
        solution.threeSum(nums);
    }
}
