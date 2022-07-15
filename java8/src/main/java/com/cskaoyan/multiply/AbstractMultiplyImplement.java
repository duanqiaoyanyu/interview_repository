package com.cskaoyan.multiply;

import com.cskaoyan.function.KaoYanConsumer;
import com.cskaoyan.function.KaoYanFunction;
import com.cskaoyan.function.KaoYanPredicate;
import com.cskaoyan.function.KaoYanSupplier;

/**
 *
 * <p>
 *     1. 类的多实现, 可联系接口的多实现 {@link com.cskaoyan.function.CompositeInterface}
 *     2.抽象类分别实现多个接口 和 实现一个多继承后的接口效果是一样的。 到底哪种比较好, 是需要根据具体情况分析的, 没有必然的答案
 *     3. 在不同的使用场景下使用不同的方式去实现一个功能会有不同的侧重点
 *
 *     作用上等效于: {@link AbstractComposite}
 * </p>
 *
 * @author duanqiaoyanyu
 * @date 2022/7/6 21:22
 */
public abstract class AbstractMultiplyImplement implements KaoYanSupplier, KaoYanConsumer, KaoYanPredicate, KaoYanFunction {

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
