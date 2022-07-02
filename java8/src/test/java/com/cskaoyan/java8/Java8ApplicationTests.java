package com.cskaoyan.java8;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Pair;
import cn.hutool.core.map.MapUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
class Java8ApplicationTests {

    @Test
    public void test1() {
        List<String> dogers = CollUtil.newArrayList("1", "2", "3");
        List<String> cats = CollUtil.newArrayList("4", "5", "6");

        HashMap<String, Object> map = MapUtil.newHashMap(2);
    }

    public static <K, V> Map<K, V> toFlatMap(Map<List<K>, V> map) {
        if (MapUtil.isEmpty(map)) {
            return Collections.emptyMap();
        }

        return map.entrySet()
                .stream()
                .flatMap(entry -> {
                    List<K> key = entry.getKey();
                    V value = entry.getValue();

                    return key
                            .stream()
                            .map(k -> Pair.of(k, value));
                })
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }
}
