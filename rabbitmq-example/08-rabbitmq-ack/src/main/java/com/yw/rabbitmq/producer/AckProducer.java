package com.yw.rabbitmq.producer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;

import static com.yw.rabbitmq.common.BaseInfo.*;

/**
 * @author yangwei
 * @date 2019-07-07 14:33
 */
public class AckProducer {
    public static void main(String[] args) throws Exception {
        // 1. 创建一个 ConnectionFactory 工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RABBITMQ_HOST_IP);
        factory.setPort(RABBITMQ_HOST_PORT);
        factory.setVirtualHost(RABBITMQ_VIRTUAL_HOST);

        // 2. 创建一个 Connection 连接
        Connection conn = factory.newConnection();

        // 3. 获取一个 Channel 信道
        Channel channel = conn.createChannel();

        // 4. 发送消息
        String msg = "hello rabbitmq consumer ack";
        for (int i = 0; i < 6; i++) {
            HashMap<String, Object> headers = new HashMap<>();
            headers.put("num", i);
            AMQP.BasicProperties props = new AMQP.BasicProperties().builder()
                    .deliveryMode(2)    // 常用模式【2: 持久化投递】
                    .contentEncoding("UTF-8")
                    .headers(headers).build();
            channel.basicPublish(RABBITMQ_ACK_EXCHANGE, RABBITMQ_ACK_ROUTING_KEY_PRODUCER, true, props, msg.getBytes());
        }
    }
}
