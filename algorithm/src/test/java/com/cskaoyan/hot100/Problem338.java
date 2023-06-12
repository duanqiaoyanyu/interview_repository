package com.cskaoyan.hot100;

public class Problem338 {

    class Solution {
        public int[] countBits(int n) {
            int[] result = new int[n + 1];

            for (int i = 0; i <= n; i++) {
                result[i] = extract1Count(i);
            }

            return result;
        }

        private int extract1Count(int n) {
            int count = 0;

            while (n != 0) {
                int remainder = n % 2;
                if (remainder == 1) {
                    count++;
                }
                n /= 2;
            }

            return count;
        }
    }
}
