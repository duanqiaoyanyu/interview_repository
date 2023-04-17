package com.cskaoyan;

import lombok.val;

public class Problem11 {

    class Solution {
        public int maxArea(int[] height) {
            if (height == null || height.length == 0) {
                return 0;
            }

            int length = height.length;
            int result = 0, j = length - 1;
            for (int i = 0; i < j; ) {
                int area = (j - i) * Math.min(height[i], height[j]);
                result = Math.max(result, area);

                if (height[i] <= height[j]) {
                    i++;
                } else {
                    j--;
                }
            }

            return result;
        }
    }
}
