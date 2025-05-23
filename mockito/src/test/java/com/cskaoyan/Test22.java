package com.cskaoyan;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class Test22 {

    @Mock
    private List<String> mock;



    @Test
    public void test1() {
        mock.size();

        //passes when someMethod() is called no later than within 100 ms
        //exits immediately when verification is satisfied (e.g. may not wait full 100 ms)
        verify(mock, timeout(100)).size();
        //above is an alias to:
//        verify(mock, timeout(100).times(1)).size();
//
//        //passes as soon as someMethod() has been called 2 times under 100 ms
//        verify(mock, timeout(100).times(2)).size();
//
//        //equivalent: this also passes as soon as someMethod() has been called 2 times under 100 ms
//        verify(mock, timeout(100).atLeast(2)).size();

    }

}
