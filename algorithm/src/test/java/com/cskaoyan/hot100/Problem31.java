package com.cskaoyan.hot100;

/**
 * @author duanqiaoyanyu
 * @date 2023/6/6 11:35
 */
public class Problem31 {

    class Solution {
        public void nextPermutation(int[] nums) {
            int i = nums.length - 2;
            // 找到可以交换的位置
            while (i >= 0 && nums[i] >= nums[i + 1]) {
                i--;
            }

            if (i >= 0) {
                int j = nums.length - 1;
                // 找到第一个比nums[i]大的数
                while (j >= 0 && nums[i] >= nums[j]) {
                    j--;
                }
                swap(nums, i, j);
            }

            // 交换后，i后面的数是降序的，需要反转
            reverse(nums, i + 1);
        }

        private void reverse(int[] nums, int i) {
            int left = i;
            int right = nums.length - 1;
            while (left < right) {
                swap(nums, left, right);
                left++;
                right--;
            }
        }

        private void swap(int[] nums, int i, int j) {
            int temp = nums[j];
            nums[j] = nums[i];
            nums[i] = temp;
        }
    }
}
