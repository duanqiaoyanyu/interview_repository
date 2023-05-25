package com.cskaoyan;

/**
 * 多线程中很好的工作, 而且也是懒加载。但是效率很低
 *
 * @author duanqiaoyanyu
 * @date 2023/5/25 17:18
 */
public class SynchronizedLazySingleton {

    private static SynchronizedLazySingleton instance;
    private SynchronizedLazySingleton() {}

    public static synchronized SynchronizedLazySingleton getInstance() {
        if (instance == null) {
            instance = new SynchronizedLazySingleton();
        }

        return instance;
    }
}
