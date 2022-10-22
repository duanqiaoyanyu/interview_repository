package com.cskaoyan;

import com.cskaoyan.pojo.StoreDTO;
import com.cskaoyan.pojo.UniversityDTO;
import com.cskaoyan.service.BusinessService;
import com.cskaoyan.service.ComplexService;
import com.cskaoyan.service.UniversityService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class AopApplicationTests {

    @Autowired
    private BusinessService businessService;
    @Autowired
    private UniversityService universityService;
    @Autowired
    private ComplexService complexService;


    /**
     * 测试普通切面写法
     */
    @Test
    public void test1() {
        StoreDTO dto = null;
        dto = businessService.open();
        log.info("商店开业详情: {}", dto);
    }

    /**
     * 测试注解切面写法
     */
    @Test
    public void test2() {
        UniversityDTO dto = universityService.entrance();
        System.out.println(dto);
    }

    /**
     * 测试多个切面顺序和通知顺序
     */
    @Test
    public void test3() {
        String study = complexService.study();
        log.info("学习内容: {}", study);
    }

}
