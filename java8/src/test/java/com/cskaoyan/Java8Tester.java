package com.cskaoyan;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author duanqiaoyanyu
 * @date 2022/7/15 15:52
 */
@SpringBootTest
public class Java8Tester {

    // 心得
    // 1. 可选的参数声明. 可以声明参数也可以不声明. 不声明编译器会自动识别
    // 2. 大括号. 一个参数不需要大括号, 超过一个参数需要大括号
    // 3. 花括号. 一条语句就不需要花括号, 超过一条语句就需要花括号
    // 4. 返回值. 主体只有一个表达式的返回值, 编译器自动返回值 不能自己添加 return; 如果用花括号, 则一定要指定返回值 必须有 return.

    @Test
    public void test1() {
        Java8Tester tester = new Java8Tester();

        // 类型声明
        MathOperation addition = (int a, int b) -> a + b;

        // 不用类型声明
        MathOperation subtraction = (a, b) -> a - b;

        // 大括号中的返回语句
        MathOperation multiplication = (int a, int b) -> { return a * b; };

        // 没有大括号及返回语句
        MathOperation division = (int a, int b) -> a / b;

        System.out.println("10 + 5 = " + tester.operate(10, 5, addition));
        System.out.println("10 - 5 = " + tester.operate(10, 5, subtraction));
        System.out.println("10 x 5 = " + tester.operate(10, 5, multiplication));
        System.out.println("10 / 5 = " + tester.operate(10, 5, division));

        // 不用括号
        GreetingService greetService1 = message ->
                System.out.println("Hello " + message);

        // 用括号
        GreetingService greetService2 = (message) ->
                System.out.println("Hello " + message);

        greetService1.sayMessage("Runoob");
        greetService2.sayMessage("Google");
    }

    interface MathOperation {
        int operation(int a, int b);
    }

    interface GreetingService {
        void sayMessage(String message);
    }

    private int operate(int a, int b, MathOperation mathOperation){
        return mathOperation.operation(a, b);
    }
}
