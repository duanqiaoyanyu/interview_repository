package com.cskaoyan.singleton;

/**
 * 懒加载 多线程不能正常工作
 *
 * @author duanqiaoyanyu
 * @date 2023/5/25 17:13
 */
public class LazyUnsafeSingleton {

    private static LazyUnsafeSingleton instance;
    private LazyUnsafeSingleton() {}

    public static LazyUnsafeSingleton getInstance() {
        if (instance == null) {
            instance = new LazyUnsafeSingleton();
        }

        return instance;
    }
}
