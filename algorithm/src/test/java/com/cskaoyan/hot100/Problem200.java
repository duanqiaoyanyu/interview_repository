package com.cskaoyan.hot100;

/**
 * @author duanqiaoyanyu
 * @date 2023/1/2 18:19
 */
public class Problem200 {

    class Solution {
        private char[][] grid;
        private int m;
        private int n;

        public int numIslands(char[][] grid) {
            int m = grid.length;
            int n = grid[0].length;
            this.grid = grid;
            this.m = m;
            this.n = n;
            int result = 0;

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (grid[i][j] == '1') {
                        flood(i, j);
                        result++;
                    }
                }
            }

            return result;
        }

        private void flood(int i, int j) {
            grid[i][j] = '0';

            int[] directionConstructor = new int[] {-1, 0, 1, 0, -1};
            for (int direction = 0; direction < 4; direction++) {
                int x = i + directionConstructor[direction];
                int y = j + directionConstructor[direction + 1];

                if (x >= 0 && x < m && y >= 0 && y < n && grid[x][y] == '1') {
                    flood(x, y);
                }
            }
        }
    }
}
