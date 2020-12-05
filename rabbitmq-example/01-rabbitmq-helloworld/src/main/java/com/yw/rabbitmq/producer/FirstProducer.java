package com.yw.rabbitmq.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.concurrent.TimeUnit;

import static com.yw.rabbitmq.common.BaseInfo.*;

/**
 * @author yangwei
 * @date 2019-07-07 14:33
 */
public class FirstProducer {
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
        // 参数一：指定交换机名称
        // 参数二：BuiltinExchangeType.DIRECT : 交换机类型是直连
        // 参数三：指明是否消息是否要持久化到队列上
        channel.exchangeDeclare(RABBITMQ_FIRST_EXCHANGE, BuiltinExchangeType.DIRECT, true);

        // 5. 通过 Channel 发送消息
        String hello = "Hello World";
        for (int i = 0; i < 100; i++) {
            String msg = hello + i;
            channel.basicPublish(RABBITMQ_FIRST_EXCHANGE, RABBITMQ_FIRST_ROUTING_KEY, null, msg.getBytes());
            System.out.println(msg);
            TimeUnit.MILLISECONDS.sleep(100);
        }

        // 6. 关闭资源
        channel.close();
        conn.close();
    }
}
