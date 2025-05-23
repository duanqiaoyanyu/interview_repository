package com.cskaoyan;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.when;

public class Test10 {

    @Test
    public void test() {
        List<String> mock = Mockito.mock(List.class);

        when(mock.get(0))
                .thenThrow(new RuntimeException("自定义错误"))
                .thenReturn("foo");

        //First call: throws runtime exception:
        try {
            mock.get(0);
        } catch (Exception e) {
            // ignore
        }

        //Second call: prints "foo"
        System.out.println(mock.get(0));

        //Any consecutive call: prints "foo" as well (last stubbing wins).
        System.out.println(mock.get(0));

    }
}
