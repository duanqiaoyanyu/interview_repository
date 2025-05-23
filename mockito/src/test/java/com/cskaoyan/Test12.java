package com.cskaoyan;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Mockito.doThrow;

public class Test12 {

    @Mock
    private List<String> mockedList;

    @Test
    public void test() {

        doThrow(new RuntimeException()).when(mockedList).clear();

        //following throws RuntimeException:
        mockedList.clear();
    }
}
