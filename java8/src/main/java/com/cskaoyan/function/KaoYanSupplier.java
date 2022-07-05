package com.cskaoyan.function;

@FunctionalInterface
public interface KaoYanSupplier<R> {

    /**
     * 提供
     *
     * @return
     */
    R get();
}
