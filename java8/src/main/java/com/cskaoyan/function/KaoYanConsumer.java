package com.cskaoyan.function;

@FunctionalInterface
public interface KaoYanConsumer<T> {

    /**
     * 接受
     *
     * @param t
     */
    void accept(T t);

    // 增加默认方法可以
    default void do1() {
        System.out.println("11111");
    }

    // 增加静态方法也可以
    static void do2() {
        System.out.println("222222");
    }

    // 增加一个抽象方法
    // 提示在接口中找到多个非覆盖抽象方法, 报错
    // void do3();

}
