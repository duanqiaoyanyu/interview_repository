package com.cskaoyan.hot100;

import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author duanqiaoyanyu
 * @date 2022/02/28 上午 09:23
 */
@SpringBootTest
public class Problem33 {

    class Solution {
        public int search(int[] nums, int target) {
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] == target) {
                    return i;
                }
            }

            return -1;
        }
    }
}
