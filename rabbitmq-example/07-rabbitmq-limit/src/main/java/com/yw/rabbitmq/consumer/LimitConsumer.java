package com.yw.rabbitmq.consumer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import static com.yw.rabbitmq.common.BaseInfo.*;

/**
 * @author yangwei
 * @date 2019-07-07 14:33
 */
public class LimitConsumer {
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
        channel.exchangeDeclare(RABBITMQ_LIMIT_EXCHANGE, BuiltinExchangeType.TOPIC, true, false, null);

        // 5. 声明&绑定队列
        channel.queueDeclare(RABBITMQ_LIMIT_QUEUE, true, false, false, null);
        channel.queueBind(RABBITMQ_LIMIT_QUEUE, RABBITMQ_LIMIT_EXCHANGE, RABBITMQ_LIMIT_ROUTING_KEY_CONSUMER);

        // 6. 限流设置
        // 参数一 prefetchSize： 消息的大小不做任何限制
        // 参数二 prefetchCount： 服务器给的最大的消息数，这里是一条一条的消费
        // 参数三 global： 级别为consumer
        // prefetchCount 为 1， 一次消费一条消息，如果消费者没有确认消费，将不会接受生产者给的消息
        channel.basicQos(0, 1, false);

        // 7. 设置channel，使用自定义消费者
        // 消费者要想做限流必须将自动ack设置为false
        channel.basicConsume(RABBITMQ_LIMIT_QUEUE, false, new MyConsumer(channel));
    }
}
