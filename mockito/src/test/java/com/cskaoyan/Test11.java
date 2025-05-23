package com.cskaoyan;

import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * <p>
 *     自定义 Answer
 *     允许用泛型 Answer 接口来存根
 *     至今另一个有争议的特性，没有被 mockito 原生包含的. 我们推荐存根, 通过 thenReturn() 或 thenThrow(), 应该是足够对于 test/test-drive 一些干净和简单的
 *     代码. 然而, 如果你确实有一个需要去用泛型 Answer 接口来存根，这里有个例子
 * </p>
 */
public class Test11 {

    @Test
    public void test() {
        List<String> mock = mock(List.class);

        when(mock.get(anyInt())).thenAnswer(
                new Answer() {
                    public Object answer(InvocationOnMock invocation) {
                        Object[] args = invocation.getArguments();
                        Object mock = invocation.getMock();
                        return "called with arguments: " + Arrays.toString(args);
                    }
                });

        //Following prints "called with arguments: [888]"
        System.out.println(mock.get(888));
    }
}
