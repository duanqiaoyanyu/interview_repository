package com.cskaoyan;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author duanqiaoyanyu
 * @date 2022/12/28 10:59
 */
public class Problem155 {

    class MinStack {
        private Deque<Integer> stk1;
        private Deque<Integer> stk2;

        public MinStack() {
            stk1 = new ArrayDeque<>();

            stk2 = new ArrayDeque<>();
            stk2.addFirst(Integer.MAX_VALUE);
        }

        public void push(int val) {
            stk1.addFirst(val);
            stk2.addFirst(Math.min(val, stk2.peekFirst()));
        }

        public void pop() {
            stk1.pollFirst();
            stk2.pollFirst();
        }

        public int top() {
            return stk1.peekFirst();
        }

        public int getMin() {
            return stk2.peekFirst();
        }
    }

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(val);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */
}
