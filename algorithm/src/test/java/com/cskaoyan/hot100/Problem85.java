package com.cskaoyan.hot100;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author duanqiaoyanyu
 * @date 2022/02/27 下午 01:20
 */
@SpringBootTest
public class Problem85 {

    /**
     * 给定一个仅包含 0 和 1 、大小为 rows x cols 的二维二进制矩阵，找出只包含 1 的最大矩形，并返回其面积。
     */

    class Solution {
        public int maximalRectangle(char[][] matrix) {
            int row = matrix.length;
            int col = matrix[0].length;
            int result = 0;

            for (int i = 0; i < row; i++) {
                int bottomDistance = row - 1 - i + 1;
                for (int j = 0; j < col; j++) {
                    int rightDistance = col - 1 -j + 1;

                    // 已知最大面积 大于 当前基点所能构建最大矩形, 故当前基点可直接跳过
                    if (result >= (bottomDistance * rightDistance)) {
                        continue;
                    }

                    boolean rowUnsatisfied = false;
                    boolean colUnsatisfied = false;
                    int maxColEnd = rightDistance;
                    // 这里由于长、宽都是可变的, 所以我们优先保持上下宽度不变, 先只变动左右长度
                    // 行长度范围
                    for (int l = 1; l <= bottomDistance; l++) {
                        int colStart = 1;
                        if (colUnsatisfied) {
                            colStart = maxColEnd;
                        }

                        // 列长度范围
                        for (; colStart <= maxColEnd; colStart++) {

                            boolean unsatisfied = false;
                            // 构建矩形行区间
                            for (int m = i; m < i + l; m++) {
                                // 构建矩形列区间
                                for (int n = j; n < j + colStart; n++) {
                                    if (matrix[m][n] != '1') {
                                        unsatisfied = true;

                                        if (n > j) {
                                            colUnsatisfied = true;
                                            maxColEnd = n - j;
                                            break;
                                        }

                                        rowUnsatisfied = true;
                                        break;
                                    }
                                }

                                if (unsatisfied) {
                                    break;
                                }
                            }

                            if (unsatisfied) {
                                break;
                            }

                            int area = l * colStart;
                            result = area >= result ? area : result;
                        }

                        if (rowUnsatisfied) {
                            break;
                        }
                    }
                }
            }

            return result;
        }
    }

    // 1. 首先选取矩形的基点, 基点的选择由矩阵的左上角开始 从上到下 从左到右进行遍历, 每一个点都可以是基点
    // 2. 基点选择好后构建矩形, 矩形的长、宽大小最小为 1 最大长、宽需要计算
    // 3. 计算某个基点能够构成的最大矩形 长、宽长度。相当于是分别取基点到矩阵下底边和右侧边距离的值
    // 4. 然后进行构建矩形, 得知当前构建的矩形长、宽大小。可以计算出当前矩形所占区域元素
    // 根据题意需要满足区域内元素全是1, 因为需要元素全部都是1。所以需要全部遍历 每个元素, 这个地方没有优化点, 当有一个元素出现不是1
    // 就可以直接跳过当前所构建的矩形, 判断下一个构建的矩形是否满足需求
    // 5. 遇见满足需求的矩形, 计算出矩形的面积与当前所保留的最大矩形面积进行比较, 如果比当前大则覆盖当前值。否则保持不变
    // 6. 最终所有循环结束 所得值即为最大矩形面积

    // ------------------以上基本思路实现后 下面是优化阶段
    // 1. 每次更新最大矩形面积时, 记录下最大矩形的面积。在循环下一个基点的时候, 比较当前基点能达到的最大面积 与 已知最大面积
    // 如果比已知最大面积小 则直接跳过。
    // 2. 由于循环的过程是保持行先不动 列增长的方式. 所以先判断是不是列不满足 即列大于始列。
    // 如果列不满足了 那么接下来的递归规则得变化 列只有一列
    // 如果行不满足了, 即行大于等于始行。证明遍历应该结束了, 因为此时已为所能构建的最大矩形


    @Test
    public void t1() {
        char[][] matrix = new char[][]{
                {'1','0'},{'1','0'}
        };

        Solution solution = new Solution();

        solution.maximalRectangle(matrix);
    }
}
