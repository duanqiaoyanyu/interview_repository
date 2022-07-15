package com.cskaoyan.multiply;

/**
 * @author duanqiaoyanyu
 * @date 2022/7/6 21:18
 */
public abstract class AbstractNormal {

    // 有方法体的方法
    private String do1() {
        return "你好呀";
    }

//    // Missing method body, or declare abstract
//    protected String do2();

    protected abstract String do3();
}
