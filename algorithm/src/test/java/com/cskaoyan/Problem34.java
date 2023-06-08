package com.cskaoyan;

/**
 * @author duanqiaoyanyu
 * @date 2023/6/6 15:09
 */
public class Problem34 {

    class Solution {
        public int[] searchRange(int[] nums, int target) {
            int[] result = new int[]{-1, -1};
            int left = 0, right = nums.length - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (nums[mid] == target) {
                    left = mid;
                    right = mid;

                    while (left >= 0 && nums[left] == target) {
                        left--;
                    }

                    while (right <= nums.length - 1 && nums[right] == target) {
                        right++;
                    }

                    result[0] = left + 1;
                    result[1] = right - 1;
                    break;
                } else if (nums[mid] < target) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

            return result;
        }
    }

}
