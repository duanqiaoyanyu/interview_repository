package com.cskaoyan.multiply;

/**
 * @author duanqiaoyanyu
 * @date 2022/7/6 21:34
 */
public class SimpleDefaultImplementMethod extends AbstractDefaultImplementMethod {

    @Override
    protected String do3() {
        System.out.println("覆盖父类的实现");
        return "覆盖父类的实现";
    }


    @Override
    protected String do4() {
        System.out.println("实现抽象方法");
        return "实现抽象方法";
    }
}
