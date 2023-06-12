package com.cskaoyan.hot100;

import java.util.Arrays;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 优先队列 就是一个数组形式的 平衡二叉堆
 * 如果 {@link PriorityQueue#comparator()} 是从小到大排那就是小顶堆. 如果是从大到小排, 那就是大顶堆
 * 大顶堆: 根节点元素比左右子树大, 并不要求左子树比右子树小
 * 小顶堆: 根节点元素比左右子树小, 并不要求左子树比右子树小
 *
 * @author duanqiaoyanyu
 * @date 2023/1/3 10:14
 */
public class Problem347 {
    class Solution {
        public int[] topKFrequent(int[] nums, int k) {
            Map<Integer, Long> numToCountMap = Arrays.stream(nums).boxed()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            PriorityQueue<Map.Entry<Integer, Long>> priorityQueue = new PriorityQueue<Map.Entry<Integer, Long>>(Map.Entry.comparingByValue());

            for (Map.Entry<Integer, Long> entry : numToCountMap.entrySet()) {
                priorityQueue.offer(entry);

                /**
                 * 优先队列直接遍历是没有任何顺序的, 就是数组下标从0到最后{@link PriorityQueue#queue}
                 * {@link PriorityQueue#iterator()}
                 */
                if (priorityQueue.size() > k) {
                    priorityQueue.poll();
                }
            }

            return priorityQueue.stream()
                    .mapToInt(Map.Entry::getKey)
                    .toArray();
        }

    }

}
