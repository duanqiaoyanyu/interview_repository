package com.cskaoyan;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Pair;
import cn.hutool.core.map.MapUtil;
import com.cskaoyan.function.KaoYanAction;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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

        HashMap<List<String>, String> map = MapUtil.newHashMap(2);
        map.put(dogers, "修勾");
        map.put(cats, "罗小黑");

        Map<String, String> result = toFlatMap(map);
        System.out.println(result);
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

    @Test
    public void test2() {

        // 一个接口需要一个实现类的对象
        KaoYanAction kaoYanAction1 = new KaoYanAction() {
            @Override
            public void invoke() {
                System.out.println("以前的写法");
            }
        };

        KaoYanAction kaoYanAction2 = () -> System.out.println("现在的写法");

        kaoYanAction1.invoke();
        kaoYanAction2.invoke();

        // 因为 函数式接口是有且仅有一个抽象函数的接口。那意味着只要接口只要只有一个抽象方法, 或者说是函数式接口.
        // 那么匿名内部类生成接口子类对象的方式 都可以换成 使用 lambda 表达式的方式
        // lambda 表达式该怎么写, 这个需要根据函数式接口定义的抽象函数的形式, 来决定 lambda 的写法.
        // 所以说 Lambda 表达式实际上就是匿名内部类实现抽象方法的过程。那都实现了接口的抽象方法了,
        // 那理所应当也是可以当作 Action的实现类, 而且也是匿名的。所以说 lambda 表达式也可以用接口来接收
        // lambda 表达式就是实现了抽象方法的函数式接口
        // 如果不想为了接口, 专门生成一个子类来操作, 那么可以用匿名内部类来临时实现一下接口, 来临时操作满足一下
        // 需求, 满足形参必须是这个接口, 然后又不想多定义一个专门的类的需求, 所以说在这种情况下, 使用匿名类的
        // 方式会很合适, 然后匿名类以往是通过写 new 接口 {
        //      @Override
        //      public R apply(T t) {
        //      }
        //}; 解决的.
        // 现在到了 java8。直接用 lambda 表达式来代替了以往的匿名类的方式了
    }
}
