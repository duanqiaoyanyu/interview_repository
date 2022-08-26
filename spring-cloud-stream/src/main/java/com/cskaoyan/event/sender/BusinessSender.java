package com.cskaoyan.event.sender;

import com.cskaoyan.event.model.BusinessModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * 某某业务消息发送者
 *
 */
@Slf4j
@Component
public class BusinessSender {

    /**
     * binding 名称, 对应于配置文件中的 "cskaoyanOutput"
     */
    private static final String BINDING_NAME = "cskaoyanOutput";

    @Autowired
    private StreamBridge streamBridge;

    /**
     * 发送消息
     *
     * @param model
     */
    public void send(BusinessModel model) {
        log.info("发送 [某某业务消息]... 成功, model: {}", model);
        Message<BusinessModel> message = MessageBuilder.withPayload(model).build();
        streamBridge.send(BINDING_NAME, message);
    }
}
