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
public class FanoutProducer {
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
        channel.exchangeDeclare(RABBITMQ_FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT, true);

        // 5. 发布消息
        for (int i = 0; i < 10; i++) {
            String msg = "hello, fanout-exchange" + i;
            // 不设置路由键，或者随便设置
            String routingKey = "";
            channel.basicPublish(RABBITMQ_FANOUT_EXCHANGE, routingKey, null, msg.getBytes());
        }
    }
}
