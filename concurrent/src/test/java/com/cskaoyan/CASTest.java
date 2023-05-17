package com.cskaoyan;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author duanqiaoyanyu
 * @date 2023/5/17 10:17
 */
@SpringBootTest
public class CASTest {

    @Test
    public void test1() {
        AtomicInteger atomicInteger = new AtomicInteger();

        atomicInteger.getAndAdd(2);
        atomicInteger.getAndSet(3);
    }

    /**
     * 如果 CAS 成功, return oldValue, oldValue = oldValue + addValue
     * 如果 CAS 失败, 自旋, 一直运行, 直到成功为止
     *
     * @param o
     * @param offset
     * @param delta
     * @return
     */
//    public final int getAndAddInt(Object o, long offset, int delta) {
//        int v;
//        do {
//            v = getIntVolatile(o, offset);
//        } while (!weakCompareAndSetInt(o, offset, v, v + delta));
//        return v;
//    }

    /**
     * 如果 CAS 成功, return oldValue, oldValue = newValue
     * 如果 CAS 失败, 自旋, 一直运行, 直到成功为止
     *
     * @param o
     * @param offset
     * @param newValue
     * @return
     */
//    public final int getAndSetInt(Object o, long offset, int newValue) {
//        int v;
//        do {
//            v = getIntVolatile(o, offset);
//        } while (!weakCompareAndSetInt(o, offset, v, newValue));
//        return v;
//    }
}
