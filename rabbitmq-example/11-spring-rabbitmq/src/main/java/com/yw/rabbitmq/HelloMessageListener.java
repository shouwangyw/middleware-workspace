package com.yw.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @author yangwei
 * @date 2019-07-18 09:34
 */
public class HelloMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("接收到的消息：" + new String(message.getBody()));
    }
}
