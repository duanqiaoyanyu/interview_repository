package com.cskaoyan;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class Test9 {

        @Mock
        private ArticleCalculator calculator;

        @Mock
        private ArticleDatabase database;

        @Mock
        private UserProvider userProvider;

        private ArticleManager manager;

        @Test
        public void testSomethingInJunit5() {
            MockitoAnnotations.initMocks(this);
            System.out.println(database);
        }

        public static class ArticleCalculator {

        }

        public static class ArticleDatabase {
            private String port;

            @Override
            public String toString() {
                return "ArticleDatabase{" +
                        "port='" + port + '\'' +
                        '}';
            }
        }

        public static class UserProvider {

        }

        public static class ArticleManager {

        }


}
