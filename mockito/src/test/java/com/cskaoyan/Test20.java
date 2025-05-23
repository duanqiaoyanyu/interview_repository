package com.cskaoyan;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Answers.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

public class Test20 {

    @Test
    public void test1() {
        List serializableMock = mock(List.class, withSettings().serializable());

        List<Object> list = new ArrayList<Object>();
        List<Object> spy = mock(ArrayList.class, withSettings()
                .spiedInstance(list)
                .defaultAnswer(CALLS_REAL_METHODS)
                .serializable());
    }

}
