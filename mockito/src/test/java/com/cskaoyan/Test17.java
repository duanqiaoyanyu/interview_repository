package com.cskaoyan;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 *
 */
public class Test17 {

    @Test
    public void test() {

        List mock = mock(List.class);
        when(mock.size()).thenReturn(10);
        mock.add(1);

        reset(mock);
        //at this point the mock forgot any interactions and stubbing

    }
}
