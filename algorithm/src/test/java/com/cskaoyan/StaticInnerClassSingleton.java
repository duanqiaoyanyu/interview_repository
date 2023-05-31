package com.cskaoyan;

/**
 * @author duanqiaoyanyu
 * @date 2023/5/25 17:37
 */
public class StaticInnerClassSingleton {

    private static class SingletonHolder{
        private static final StaticInnerClassSingleton INSTANCE = new StaticInnerClassSingleton();
    }

    private StaticInnerClassSingleton() {}
    public static final StaticInnerClassSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
