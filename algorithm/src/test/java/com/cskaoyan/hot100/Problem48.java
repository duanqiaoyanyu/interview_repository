package com.cskaoyan.hot100;

/**
 * @author duanqiaoyanyu
 * @date 2023/6/12 11:21
 */
public class Problem48 {

    class Solution {
        public void rotate(int[][] matrix) {
            int n = matrix.length;
            // 水平翻转
            for (int i = 0; i < n / 2; ++i) {
                for (int j = 0; j < n; ++j) {
                    int tmp = matrix[i][j];
                    matrix[i][j] = matrix[n - i - 1][j];
                    matrix[n - i - 1][j] = tmp;
                }
            }

            // 主对角线翻转
            for (int i = 0; i < n; ++i) {
                for (int j = i + 1; j < n; ++j) {
                    int tmp = matrix[i][j];
                    matrix[i][j] = matrix[j][i];
                    matrix[j][i] = tmp;
                }
            }
        }
    }
}
