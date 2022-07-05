package com.cskaoyan.function;

@FunctionalInterface
public interface KaoYanAction {

    /**
     * 不需入参和出参, 单纯执行某段代码
     */
    void invoke();
}
