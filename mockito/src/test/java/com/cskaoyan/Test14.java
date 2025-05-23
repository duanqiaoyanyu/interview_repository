package com.cskaoyan;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;

public class Test14 {

    @Test
    public void test() {

        Foo mock = mock(Foo.class, Mockito.RETURNS_SMART_NULLS);
        Foo mockTwo = mock(Foo.class, invocation -> {
            System.out.println("1111");
            return "mockTwo";
        });

        System.out.println(mock);
        System.out.println(mockTwo);

    }

    static class Foo {
        public String foo() {
            return "foo";
        }
    }

}
