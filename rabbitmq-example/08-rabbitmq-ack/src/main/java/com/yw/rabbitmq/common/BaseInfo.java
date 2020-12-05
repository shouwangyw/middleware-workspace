package com.yw.rabbitmq.common;

/**
 * @author yangwei
 * @date 2019-07-07 15:36
 */
public interface BaseInfo {
    String RABBITMQ_HOST_IP = "192.168.254.125";
    int RABBITMQ_HOST_PORT = 5672;
    String RABBITMQ_VIRTUAL_HOST = "/";
    String RABBITMQ_ACK_EXCHANGE = "ack-exchange";
    String RABBITMQ_ACK_ROUTING_KEY_PRODUCER = "ack.key";
    String RABBITMQ_ACK_ROUTING_KEY_CONSUMER = "ack.#";
    String RABBITMQ_ACK_QUEUE = "ack-queue";
}
