package com.cskaoyan.service.impl;

import com.cskaoyan.service.ComplexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author duanqiaoyanyu
 * @date 2022/10/18 14:47
 */
@Slf4j
@Service
public class ComplexServiceImpl implements ComplexService {


    @Override
    public String study() {
        log.info("我来带你研究研究");
        return "calculus";
    }
}
