package com.cskaoyan;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class Test8 {

    @Test
    public void test1() {
        List<String> mockedList = mock(List.class);
        //using mocks
        mockedList.add("one");
        mockedList.add("two");

        verify(mockedList).add("one");
        //verify(mockedList).add("two");

        //following verification will fail
        verifyNoMoreInteractions(mockedList);

    }
}
