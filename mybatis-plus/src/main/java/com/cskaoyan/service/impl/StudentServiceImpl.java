package com.cskaoyan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cskaoyan.entity.Student;
import com.cskaoyan.mapper.StudentMapper;
import com.cskaoyan.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author duanqiaoyanyu
 * @date 2022/12/4 19:17
 */
@Slf4j
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {


    @Override
    public Student query(Long id) {
        if (Objects.isNull(id)) {
            return null;
        }

        return getById(id);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void modify(Long id, String name) {
        if (Objects.isNull(id)) {
            return;
        }

        Student student = query(id);
        baseMapper.alwaysUpdateSomeColumnById(student);
    }
}
