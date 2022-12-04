package com.cskaoyan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cskaoyan.entity.Student;

/**
 * @author duanqiaoyanyu
 * @date 2022/12/4 19:17
 */
public interface StudentService extends IService<Student> {

    /**
     * 查询
     *
     * @param id
     * @return
     */
    Student query(Long id);

    /**
     * 更新
     *
     * @param id 主键ID
     * @param name 姓名
     */
    void modify(Long id, String name);
}
