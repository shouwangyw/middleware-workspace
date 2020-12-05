package com.yw.rabbitmq.consumer;

import com.rabbitmq.client.*;

import static com.yw.rabbitmq.common.BaseInfo.*;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author yangwei
 * @date 2019-07-07 14:33
 */
public class FirstConsumer {
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
        channel.exchangeDeclare(RABBITMQ_FIRST_EXCHANGE, BuiltinExchangeType.DIRECT, true);

        // 5. 声明一个队列
        // 参数一：队列名
        // 参数二：指明是否消息要持久化到队列上，关机后消息也不会丢
        // 参数三：是否独占，类似加了一把锁，只有一个 Channel 能监听，保证了消费顺序
        // 参数四：是否自动删除，如果队列跟交换机没有绑定关系，是否自动删除
        // 参数五：扩展参数
        channel.queueDeclare(RABBITMQ_FIRST_QUEUE_NAME, true, false, false, null);

        // 6. 队列绑定
        channel.queueBind(RABBITMQ_FIRST_QUEUE_NAME, RABBITMQ_FIRST_EXCHANGE, RABBITMQ_FIRST_ROUTING_KEY);

        // 7. 异步获取投递消息
        // 就相当于根据 路由key 获取信道中的数据
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), UTF_8);

            System.out.println(" [x] Received '" + message + "'");
            try {
                System.out.println(message);
            } finally {
                System.out.println(" [x] Done!");
            }
        };
        boolean autoAck = true;

        channel.basicConsume(RABBITMQ_FIRST_QUEUE_NAME, autoAck, deliverCallback, consumerTag -> {
        });

//        // 8. 关闭资源
//        channel.close();
//        conn.close();
    }
}
