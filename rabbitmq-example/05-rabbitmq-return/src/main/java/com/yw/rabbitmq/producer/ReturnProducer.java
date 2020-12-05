package com.yw.rabbitmq.producer;

import com.rabbitmq.client.*;

import java.io.IOException;

import static com.yw.rabbitmq.common.BaseInfo.*;

/**
 * @author yangwei
 * @date 2019-07-07 14:33
 */
public class ReturnProducer {
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

        // 5. 发送一条消息
        String msg = "Hello Return Message";
        boolean mandatory = true;

        // 6. 添加一个return监听器
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.err.println("========return 机制========");
                System.err.println("replyCode : " + replyCode);
                System.err.println("replyText : " + replyText);
                System.err.println("exchange : " + exchange);
                System.err.println("routingKey : " + routingKey);
                System.err.println("properties : " + properties);
                System.err.println("body : " + new String(body));
            }
        });
//        channel.basicPublish(RABBITMQ_RETURN_EXCHANGE, RABBITMQ_RETURN_ROUTING_KEY_PRODUCER, mandatory,null, msg.getBytes());
        channel.basicPublish(RABBITMQ_RETURN_EXCHANGE, RABBITMQ_RETURN_ROUTING_KEY_ERROR, mandatory, null, msg.getBytes());
    }
}
