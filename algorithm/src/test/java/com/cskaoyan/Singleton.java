package com.cskaoyan;

/**
 * @author duanqiaoyanyu
 * @date 2023/5/25 17:46
 */
public class Singleton {

    private volatile static Singleton instance;
    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }

        return instance;
    }
}
