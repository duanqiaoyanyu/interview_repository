package com.cskaoyan;

import com.cskaoyan.pojo.StoreDTO;
import com.cskaoyan.service.BusinessService;
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

    @Test
    public void test1() {
        StoreDTO dto = null;
        dto = businessService.open();
        log.info("商店开业详情: {}", dto);
    }

}
