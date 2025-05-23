package com.cskaoyan;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class Test5 {

    @Test
    public void test() {
        List<String> mockedList = mock(List.class);

        doThrow(new RuntimeException()).when(mockedList).clear();

        //following throws RuntimeException:
        mockedList.clear();

    }
}
