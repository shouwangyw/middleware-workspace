package com.yw.rabbitmq.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import static com.yw.rabbitmq.common.BaseInfo.*;

/**
 * @author yangwei
 * @date 2019-07-07 14:33
 */
public class DirectProducer {
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
        channel.exchangeDeclare(RABBITMQ_DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT, true);

        // 5. 通过 Channel 发送消息
        String msg = "hello, direct-exchange";
        channel.basicPublish(RABBITMQ_DIRECT_EXCHANGE, RABBITMQ_DIRECT_ROUTING_KEY, null, msg.getBytes());

//        // 6. 关闭资源
//        channel.close();
//        conn.close();
    }
}
