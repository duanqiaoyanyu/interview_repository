package com.cskaoyan.service;

import com.cskaoyan.event.model.BusinessModel;

public interface BusinessService {

    /**
     * 处理某某业务
     *
     * @param model
     */
    void handle(BusinessModel model);
}
