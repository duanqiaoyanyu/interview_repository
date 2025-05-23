package com.cskaoyan;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

public class Test25 {

    @Mock
    Car mock;
    @Mock
    Car mockTwo;

    @Test
    public void test() {
        verify(mock).foo();
        verify(mockTwo).bar();

        //ignores all stubbed methods:
        verifyNoMoreInteractions(ignoreStubs(mock, mockTwo));

        //creates InOrder that will ignore stubbed
        InOrder inOrder = inOrder(ignoreStubs(mock, mockTwo));
        inOrder.verify(mock).foo();
        inOrder.verify(mockTwo).bar();
        inOrder.verifyNoMoreInteractions();

    }

    static class Car {
        public void foo() {
        }
        public void bar() {
        }
    }
}
