package com.cskaoyan;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

public class Tedt16 {

    @Test
    public void test() {

        //you can create partial mock with spy() method:
        List list = spy(new LinkedList());

        //you can enable partial mock capabilities selectively on mocks:
        Foo mock = mock(Foo.class);
        //Be sure the real implementation is 'safe'.
        //If real implementation throws exceptions or depends on specific state of the object then you're in trouble.
        when(mock.someMethod()).thenCallRealMethod();
    }

    static class Foo {
        public String someMethod() {
            return "Hello World";
        }
    }

}
