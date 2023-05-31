package com.cskaoyan.controller;

import cn.hutool.extra.spring.SpringUtil;
import com.cskaoyan.config.KaoYanComponent;
import com.cskaoyan.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author duanqiaoyanyu
 * @date 2022/11/8 19:33
 */
@Slf4j
@RequestMapping("/kaoyan")
@RestController
public class KaoYanController {

    @Autowired
    private TeacherService teacherService;


    @GetMapping("/async")
    public void async() {
        log.info("异步调用");
        teacherService.teach();
    }

    @GetMapping("/test")
    public void test1() {
        KaoYanComponent yanComponent = SpringUtil.getBean("kaoYanComponent", KaoYanComponent.class);
        System.out.println(yanComponent.getName());
    }
}
