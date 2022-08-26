package com.cskaoyan.service.impl;

import com.cskaoyan.event.model.BusinessModel;
import com.cskaoyan.service.BusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 某某业务
 *
 */
@Slf4j
@Service
public class BusinessServiceImpl implements BusinessService {

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void handle(BusinessModel model) {
        // 注意业务幂等, 这里我就不写具体操作了。只是简单打印一下

        Long id = model.getId();
        String content = model.getContent();
        log.info("===id: {}===", id);
        log.info("===content: {}===", content);
    }
}
