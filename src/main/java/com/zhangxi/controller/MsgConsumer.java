package com.zhangxi.controller;

import com.zhangxi.service.TestService;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MsgConsumer implements InitializingBean {

    @Value("${spring.rocketmq.cluster}")
    private String cluster;
    @Value("${spring.rocketmq.namesrvAddr}")
    private String namesrvAddr;
    @Value("${spring.rocketmq.instanceName}")
    private String instanceName;
    @Value("${spring.rocketmq.topic}")
    private String topic;
    @Value("${spring.rocketmq.fcTag}")
    private String fcTag;

    @Autowired
    private TestService service;

    @Override
    public void afterPropertiesSet() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(cluster);
        consumer.setNamesrvAddr(namesrvAddr);

        try {
            consumer.setInstanceName(instanceName);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.setMessageModel(MessageModel.CLUSTERING);
            consumer.subscribe(topic, "*");
            consumer.setConsumeThreadMax(5);
            consumer.setConsumeThreadMin(5);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    MessageExt msg = list.get(0);
                    if (msg.getTopic().equals(topic)) {
                        if (msg.getTags().equals(fcTag)) {
                            System.out.println(new String(msg.getBody()));
                            // 根据消息内容处理其他逻辑
                            service.getMobile(Integer.parseInt(new String(msg.getBody())));
                        }
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
