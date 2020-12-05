package com.yw.rabbitmq.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;

import static com.yw.rabbitmq.common.BaseInfo.*;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author yangwei
 * @date 2019-07-07 14:33
 */
public class SecondConsumer {
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
        channel.exchangeDeclare(RABBITMQ_SECOND_EXCHANGE, BuiltinExchangeType.DIRECT, true);

        // 5. 声明一个队列
        String queueName = channel.queueDeclare().getQueue();

        // 6. 队列绑定
        channel.queueBind(queueName, RABBITMQ_SECOND_EXCHANGE, RABBITMQ_SECOND_ROUTING_KEY);

        // 7. 消费消息
        while (true) {
            boolean autoAck = false;
            String consumerTag = "";
            channel.basicConsume(queueName, autoAck, consumerTag, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String routingKey = envelope.getRoutingKey();
                    String contentType = properties.getContentType();
                    System.out.println("消费的路由键：" + routingKey + " 消费的内容类型：" + contentType);

                    long deliveryTag = envelope.getDeliveryTag();

                    // 确认消息
                    channel.basicAck(deliveryTag, false);

                    System.out.println("消费的消息体：" + new String(body, UTF_8));
                }
            });
        }
    }
}
