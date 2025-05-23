package com.cskaoyan;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class Test7 {

    @Test
    public void test() {
        List<String> mockOne = mock(List.class);

        //using mocks - only mockOne is interacted
        mockOne.add("one");

        //ordinary verification
        verify(mockOne).add("one");

        //verify that method was never called on a mock
        verify(mockOne, never()).add("two");

    }
}
