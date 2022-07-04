package com.cskaoyan;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Pair;
import cn.hutool.core.lang.copier.Copier;
import cn.hutool.core.map.MapUtil;
import com.cskaoyan.function.KaoYanAction;
import com.cskaoyan.function.KaoYanFunction;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
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

    @Test
    public void test3() {
        List<List<List<String>>> list = new ArrayList<>();
        List<List<String>> collect = list
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    // 所以说 flatMap 要理解好他的功能和用处, 只需要关注他方法的定义就好了
    // <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);
    // R 是 stream 流要返回的对象类型. T 是 stream 在进行 map 操作之前里面保存的对象类型
    // 所以进行 flatMap 操作结束后是一个 R 类型的流, 不是 List<R>. 这点和 map 保持一致, map 里面也是 R
    // 然后入参是一个 Function 函数式接口, 我们通常用 lambda 表达式去实现它。Function函数通常有一个
    // 入参类型 和 一个出惨类型。这里入参类型就是 当前 stream 流里对象的类型, 然后出参呢就是一个 Stream 流的一个
    // 对象 然后 stream 流里面的对象类型也就是我们 flatMap 最终要返回的 stream 流类型的对象. 但是呢, 相当于是
    // 当前 还未进行 flatMap 操作的 stream 流里的每一个元素经过转化都能得到一个 R 类型对象的流对象, 然后呢
    // flatMap 的作用呢就是能够把这些流对象里面的所有的 R 对象全部取出来, 然后汇总到未进行 flatMap 操作前的
    // 那条流里面去。这样相当于是 转化前的所有对象 经过转化后的所有映射集 全部平铺到一个流里面去了。
    // 另外 flatMap 也是有相对的概念的, 不是说无脑的把无论多少层级的集合结构全部压成只有一层, 这种理解是不对的。
    // 还是要回归到方法定义的本身上去看, 分清当前的 T 类型是什么, 而想得到的 R 又是什么类型。

    // 总结: 看清方法定义 分清 T类型是谁, R类型是谁
    //      解决了什么问题, 解决了以往入参每一个元素的映射结果都是一个集合, 然后最终我们只关心映射集合的汇总结果,
    //      以往的 map 就会导致, 我们依然需要再进行一次遍历, 将每一个映射结果集合汇总到一个新的集合中。
    //      有了 flatMap 就能直接跳过再一次遍历的过程, 直接将映射结果集合里面的元素汇总到一个流中去。


    @Test
    public void test4() {
        KaoYanFunction<Integer, String> function = Java8ApplicationTests::referencedFunction;
    }

    public static String referencedFunction(Integer param) {
        return "response";
    }

    // 方法引用是一个 lambda 表达式, 然后也是为了解决简化 函数式接口实现类的问题,
    // 本来我们的 lambda 表达式相比较于传统的写一个匿名内部类对象的方式比较简洁了,
    // 那我们的 方法引用解决了什么问题呢? 那就是我们的 lambda 表达式中使用到了已经存在的
    // 方法并且, 我们引用的方法能够解决函数式接口定义的抽象方法, 意思就是说我们引用的
    // 方法的入参和出参都和函数式接口定义的入参出参一致, 这样的情况下我们就可以用方法引用代替 lambda 表达式,
    // 我们读取代码的时候遇到方法引用怎么理解呢, 相当于我们可以理解为他就是执行了引用方法的内容, 比较直观,
    // 就相当于我只要只要调用了某个方法, 而我并不用像传统的 lambda 表达式去关心他做了什么, 至于到底做了什么, 可以
    // 在具体的方法定义中查看, 比起 lambda 表达式再去嵌套一层再去调用方法会显得更加简洁。

    // 什么时候适合使用。 需要一个函数式接口, 并且方法可以覆盖函数式接口的定义
    // 什么时候不适合使用。方法不能覆盖函数式接口的定义, 无论是入参还是出参不满足。都不适合

    // 注意: 有些时候你看起来好像方法引用的方法 "看起来" 好像没有覆盖函数式对应的接口,
    // 不用担心只要 IDEA 没报错, 那他就是对的。只不过有些隐含参数你没看到而已, 或者是一些特殊的函数
    // 例如 <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);
    // 传入 Collection::stream 也是对的。所以不要怀疑,
    // 另外 String.compareTo() 和 int Comparator<String>.compare(String, String) 也能匹配
}
