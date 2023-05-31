package com.cskaoyan;

/**
 * 基于 classloader 机制避免了多线程的同步问题, 不过, instance 在类装载时就实例化, 虽然导致类装载的原因有很多种, 在单例模式中大多数都是调用
 * getInstance 方法, 但是也不能确定有其他的方式(或者其他的静态方法) 导致类装载, 着时候初始化 instance 显然没有达到 lazy loading的效果
 *
 * @author duanqiaoyanyu
 * @date 2023/5/25 17:26
 */
public class EagerSingleton {

    private static EagerSingleton instance = new EagerSingleton();
    private EagerSingleton() {}

    public static EagerSingleton getInstance() {
        return instance;
    }
}
