package com.cskaoyan;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

public class Test3 {

    @Test
    public void test() {
        List<String> mockedList = mock(List.class);

        //stubbing using built-in anyInt() argument matcher
        when(mockedList.get(anyInt())).thenReturn("element");

        //stubbing using custom matcher (let's say isValid() returns your own matcher implementation):
        when(mockedList.contains(argThat(isValid()))).thenReturn(true);

        //following prints "element"
        System.out.println(mockedList.get(999));
        System.out.println(mockedList.contains("123456"));

        //you can also verify using an argument matcher
        verify(mockedList).get(anyInt());

        //argument matchers can also be written as Java 8 Lambdas
        //verify(mockedList).add(argThat(someString -> someString.length() > 5));

    }

    /**
     * 自定义参数匹配器
     *
     * @return
     */
    ArgumentMatcher<String> isValid() {
        return new ArgumentMatcher<String>() {
            @Override
            public boolean matches(String argument) {
                return argument.length() > 5;
            }
        };
    }

}
