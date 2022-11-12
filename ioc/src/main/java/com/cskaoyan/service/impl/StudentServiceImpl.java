package com.cskaoyan.service.impl;

import com.cskaoyan.service.StudentService;
import com.cskaoyan.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author duanqiaoyanyu
 * @date 2022/11/8 11:44
 */
@Slf4j
@Service
//@RefreshScope
public class StudentServiceImpl implements StudentService {


    @Autowired
    private TeacherService teacherService;

    @Async
    @Override
    public void learn() {
        log.info("学而时习之, 不亦乐乎");
    }
}
