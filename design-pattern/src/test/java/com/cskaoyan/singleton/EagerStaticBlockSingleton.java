package com.cskaoyan.singleton;

/**
 * 表面上看起来差别挺大, 饿汉 {@link EagerSingleton}差不多。都是在类初始化即实例化 instance
 *
 * @author duanqiaoyanyu
 * @date 2023/5/25 17:30
 */
public class EagerStaticBlockSingleton {

    private static EagerStaticBlockSingleton instance = null;
    static {
        instance = new EagerStaticBlockSingleton();
    }

    private EagerStaticBlockSingleton() {}
    public static EagerStaticBlockSingleton getInstance() {
        return instance;
    }
}
