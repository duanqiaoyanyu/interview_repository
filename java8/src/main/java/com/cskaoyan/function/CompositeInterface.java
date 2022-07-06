package com.cskaoyan.function;

/**
 * <p>
 *     1. 接口多继承 弥补了 Java 中的类不能进行多继承的缺点了
 *     2. 接口的多继承, 可以联系类的多实现 {@link com.cskaoyan.multiply.AbstractMultiplyImplement}
 * </p>
 *
 * @author duanqiaoyanyu
 * @date 2022/7/6 21:09
 */
public interface CompositeInterface extends KaoYanSupplier, KaoYanConsumer, KaoYanPredicate, KaoYanFunction{

}
