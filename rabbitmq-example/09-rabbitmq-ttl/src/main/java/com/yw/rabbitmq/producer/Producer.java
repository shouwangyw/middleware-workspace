package com.yw.rabbitmq.producer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

import static com.yw.rabbitmq.common.BaseInfo.*;

/**
 * @author yangwei
 * @date 2019-07-07 14:33
 */
public class Producer {
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
        String msg = "hello rabbitmq ttl";
        Map<String, Object> headers = new HashMap<>();
        headers.put("name", "yw");
        AMQP.BasicProperties props = new AMQP.BasicProperties().builder()
                // 设置编码为UTF8
                .contentEncoding("UTF-8")
                // 设置自定义Header
                .headers(headers)
                // 设置消息失效时间
                .expiration("5000").build();
        // 设置了消息超时时间为5秒, 5秒后消息自动删除
        channel.basicPublish(RABBITMQ_TTL_EXCHANGE, RABBITMQ_TTL_ROUTING_KEY_PRODUCER, props, msg.getBytes());
        // 没有设置消息存活时间,消息存活时间根据队列来决定
        channel.basicPublish(RABBITMQ_TTL_EXCHANGE, RABBITMQ_TTL_ROUTING_KEY_PRODUCER, null, msg.getBytes());
    }
}
