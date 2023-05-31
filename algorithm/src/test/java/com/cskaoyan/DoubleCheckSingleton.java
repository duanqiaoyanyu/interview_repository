package com.cskaoyan;

/**
 * 双重检查锁
 *
 * @author duanqiaoyanyu
 * @date 2023/5/25 17:42
 */
public class DoubleCheckSingleton {

    private volatile static DoubleCheckSingleton doubleCheckSingleton;
    private DoubleCheckSingleton() {}
    public static DoubleCheckSingleton getInstance() {
        if (doubleCheckSingleton == null) {
            synchronized (DoubleCheckSingleton.class) {
                if (doubleCheckSingleton == null) {
                    doubleCheckSingleton = new DoubleCheckSingleton();
                }
            }
        }

        return doubleCheckSingleton;
    }
}
