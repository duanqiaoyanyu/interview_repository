package com.cskaoyan.multiply;

import com.cskaoyan.function.CompositeInterface;

/**
 * 抽象类可以没有抽象方法, 但是有抽象方法的类一定是抽象类
 * 抽象类可以实现接口的抽象方法, 也可以不实现接口的抽象方法, 同样自己的定义的抽象方法可以实现也可以不实现
 *
 * 实现了一个多继承后的接口相当于是 一次性实现了多个接口
 *
 * @author duanqiaoyanyu
 * @date 2022/7/6 21:13
 */
public abstract class AbstractComposite implements CompositeInterface {

    @Override
    public void accept(Object o) {

    }

    @Override
    public Object apply(Object o) {
        return null;
    }

    @Override
    public boolean test(Object o) {
        return false;
    }

    @Override
    public Object get() {
        return null;
    }

}
