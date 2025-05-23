package com.cskaoyan;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;
import static org.mockito.mock.SerializableMode.ACROSS_CLASSLOADERS;

public class Test31 {

    @Test
    public void test() {

        // use regular serialization
        mock(Book.class, withSettings().serializable());

        // use serialization across classloaders
        mock(Book.class, withSettings().serializable(ACROSS_CLASSLOADERS));

    }

    static class Book {
        private String title;
        private String author;
    }

}
