package com.yw.rabbitmq.consumer;

import com.rabbitmq.client.BuiltinExchangeType;
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
public class Consumer {
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

        // 4. 声明一个交换机
        channel.exchangeDeclare(RABBITMQ_TTL_EXCHANGE, BuiltinExchangeType.TOPIC, true, false, null);

        // 5. 声明&绑定队列
        Map<String, Object> arguments = new HashMap<>();
        // 设置队列超时时间为10秒
        arguments.put("x-message-ttl", 10000);
        channel.queueDeclare(RABBITMQ_TTL_QUEUE, true, false, false, arguments);
        channel.queueBind(RABBITMQ_TTL_QUEUE, RABBITMQ_TTL_EXCHANGE, RABBITMQ_TTL_ROUTING_KEY_CONSUMER);

        // 6. 手工签收。必须要关闭 autoAck =false
        channel.basicConsume(RABBITMQ_TTL_QUEUE, false, new MyConsumer(channel));
    }
}
