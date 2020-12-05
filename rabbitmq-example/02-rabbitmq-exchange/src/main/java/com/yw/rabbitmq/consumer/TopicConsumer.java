package com.yw.rabbitmq.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;

import static com.yw.rabbitmq.common.BaseInfo.*;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author yangwei
 * @date 2019-07-07 14:33
 */
public class TopicConsumer {
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
        channel.exchangeDeclare(RABBITMQ_TOPIC_EXCHANGE, BuiltinExchangeType.TOPIC, true);

        // 5. 声明&绑定队列
//        String routingKey = "topic-msg.*";
        String routingKey = "topic-msg.#";
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, RABBITMQ_TOPIC_EXCHANGE, routingKey);

        // 6. 消费消息
        while (true) {
            boolean autoAck = false;
            String consumerTag = "";
            channel.basicConsume(queueName, autoAck, consumerTag, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    // 获取 routingKey & contentType
                    String routingKey = envelope.getRoutingKey();
                    String contentType = properties.getContentType();
                    System.out.println("消费的 Routing Key：" + routingKey + " \n消费的 Content Type：" + contentType);

                    // 获取传送标签
                    long deliveryTag = envelope.getDeliveryTag();

                    // 确认消息
                    channel.basicAck(deliveryTag, false);

                    System.out.println("消费的 Body：");
                    String bodyMsg = new String(body, UTF_8);
                    System.out.println(bodyMsg);
                }
            });
        }
    }
}
