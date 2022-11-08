package com.cskaoyan.service.impl;

import com.cskaoyan.service.StudentService;
import com.cskaoyan.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author duanqiaoyanyu
 * @date 2022/11/8 11:45
 */
@Slf4j
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private StudentService studentService;

    @Async
    @Override
    public void teach() {
        log.info("师者, 传道授业解惑也!");
    }
}
