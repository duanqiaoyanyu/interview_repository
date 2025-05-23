package com.cskaoyan;

import org.junit.jupiter.api.Test;

/**
 * ?
 */
public class Test15 {

    @Test
    public void test() {
//        ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
//        verify(mock).doSomething(argument.capture());
//        assertEquals("John", argument.getValue().getName());
    }

    static class Person {
        private String name;
        public String getName() {
            return name;
        }
    }

}
