package com.cskaoyan.service.impl;

import com.cskaoyan.pojo.StoreDTO;
import com.cskaoyan.service.BusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author duanqiaoyanyu
 * @date 2022/10/11 17:44
 */
@Slf4j
@Service
public class BusinessServiceImpl implements BusinessService {

    @Override
    public StoreDTO open() {
        System.out.println("[目标方法] 开始营业啦!");

        StoreDTO dto = new StoreDTO();
        dto.setId(9999L);
        dto.setName("聚散又依依");

        // int i = 1 / 0;

        return dto;
    }
}
