package com.cskaoyan;

import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author duanqiaoyanyu
 * @date 2022/02/26 上午 10:52
 */
@SpringBootTest
public class Problem221 {

    class Solution {
        public int maximalSquare(char[][] matrix) {
            int row = matrix.length;
            int col = matrix[0].length;
            int maxSideLength = 0;
            int result = 0;


            for (int i = 0; i < row; i++) {
                int maxBottomDistance = row - 1 -i + 1;
                for (int j = 0; j < col; j++) {
                    int maxRightSideDistance = col - 1 -j + 1;
                    int currentMaxSideLength = maxBottomDistance >= maxRightSideDistance ? maxRightSideDistance : maxBottomDistance;
                    if (currentMaxSideLength <= maxSideLength) {
                        continue;
                    }

                    // 正方形边长为 k
                    for (int k = maxSideLength + 1; k <= currentMaxSideLength; k++) {
                        boolean satisfied = true;
                        // 遍历正方形区域内每个元素的值是否均为 '1'
                        for (int l = i; l < i + k; l++) {
                            for (int m = j; m < j + k; m++) {
                                if (matrix[l][m] != '1') {
                                    satisfied = false;
                                    break;
                                }
                            }

                            if (!satisfied) {
                                break;
                            }
                        }

                        if (!satisfied) {
                            break;
                        }

                        int area = k * k;
                        maxSideLength = k;
                        result = area >= result ? area : result;
                    }
                }
            }

            return result;
        }
    }

    // 1. 首先选取正方形的基点, 基点的选择由矩阵的左上角开始 从上到下 从左到右进行遍历, 每一个点都可以是基点
    // 2. 基点选择好后构建正方形, 正方形的边长大小最小为 1 最大边长需要计算
    // 3. 计算某个基本能够构成的最大正方形边长长度。相当于是取基点到矩阵下底边和右侧边距离的最小值
    // 4. 然后进行构建正方形, 得知当前构建的正方形边长大小。可以计算出当前正方形所占区域元素
    // 根据题意需要满足区域内元素全是1, 因为需要元素全部都是1。所以需要全部遍历 每个元素, 这个地方没有优化点, 当有一个元素出现不是1
    // 就可以直接跳过当前所构建的正方形, 判断下一个构建的正方形是否满足需求
    // 5. 遇见满足需求的正方形, 计算出正方形的面积与当前所保留的最大正方形面积进行比较, 如果比当前大则覆盖当前值。否则保持不变
    // 6. 最终所有循环结束 所得值即为最大正方形面积

    // ------------------以上基本思路实现后 下面是优化阶段
    // 1. 每次更新最大正方形面积时, 记录下最大正方形的边长。在循环下一个基点的时候, 比较当前基点能达到的最大边长 与 已知最大边长
    // 如果比已知最大边长小 则直接跳过。 并且之后的正方形构建的过程边长也从已经最大边长 + 1 开始
}
