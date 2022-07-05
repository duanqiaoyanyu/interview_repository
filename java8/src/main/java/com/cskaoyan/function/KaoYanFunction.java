package com.cskaoyan.function;

@FunctionalInterface
public interface KaoYanFunction<T, R> {

    /**
     * 应用
     *
     * @param t
     * @return
     */
    R apply(T t);

    // 这里为什么是静态方法而不是默认方法, 就是看你对 java8 interface的理解了
    // 静态方法不需要实现类就可以进行调用, 而默认方法是需要实现类才能进行调用的。理解这点就好分辨了

    /**
     * f(x) = x;
     *
     * @return
     * @param <X>
     */
    static <X> KaoYanFunction<X, X> identify() {
        return x -> x;
    }


//    /**
//     * f(x) = x;
//     *
//     * @return
//     */
//    // 注意这里的泛型 T 是泛型方法的 T, 而不是接口上面的 T, 可以通过把注释放开, 然后鼠标左键定位就知道
//    // T 是 用的定义在泛型方法的 T了, 同样点击 R, 由于泛型方法没有 R, 只能取用接口的 R,也是鼠标定位就知道
//    // 泛型方法的泛型优先使用的是定义在方法申明处的, 其次才是类或者接口的
//    // 为了更好理解 identify() 请看上面的写法
//    static <T> KaoYanFunction<T, R> identify() {
//        return x -> x;
//    }
}
