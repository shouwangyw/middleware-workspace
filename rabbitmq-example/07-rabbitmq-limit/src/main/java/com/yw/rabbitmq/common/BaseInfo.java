package com.yw.rabbitmq.common;

/**
 * @author yangwei
 * @date 2019-07-07 15:36
 */
public interface BaseInfo {
    String RABBITMQ_HOST_IP = "192.168.254.125";
    int RABBITMQ_HOST_PORT = 5672;
    String RABBITMQ_VIRTUAL_HOST = "/";
    String RABBITMQ_LIMIT_EXCHANGE = "limit-exchange";
    String RABBITMQ_LIMIT_ROUTING_KEY_PRODUCER = "limit.key";
    String RABBITMQ_LIMIT_ROUTING_KEY_CONSUMER = "limit.#";
    String RABBITMQ_LIMIT_QUEUE = "limit-queue";
}
