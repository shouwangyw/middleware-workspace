package com.yw.rabbitmq.producer;

import com.rabbitmq.client.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static com.yw.rabbitmq.common.BaseInfo.*;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author yangwei
 * @date 2019-07-07 14:33
 */
public class MessageProducer {
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

        // 4. 声明交换机
        channel.exchangeDeclare(RABBITMQ_DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT, true);

        // 5. 添加headers信息
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("msg-01", "hello");
        headers.put("msg-02", "world");

        // 6. 添加额外的属性信息
        AMQP.BasicProperties props = new AMQP.BasicProperties.Builder()
                .deliveryMode(2) // 持久化消息
                .contentEncoding(String.valueOf(UTF_8))
                .expiration("10000")
                .headers(headers)
                .build();

        // 5. 通过 Channel 发送消息
        String hello = "Hello, message";
        for (int i = 0; i < 100; i++) {
            String msg = hello + i;
            channel.basicPublish(RABBITMQ_DIRECT_EXCHANGE, RABBITMQ_DIRECT_ROUTING_KEY, props, msg.getBytes());
            System.out.println(msg);
            TimeUnit.MILLISECONDS.sleep(100);
        }
    }
}
