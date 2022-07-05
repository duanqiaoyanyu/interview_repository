package com.cskaoyan.function;

@FunctionalInterface
public interface KaoYanPredicate<T> {

    /**
     * 测试
     *
     * @param t
     * @return
     */
    boolean test(T t);
}
