package com.yw.rabbitmq.common;

/**
 * @author yangwei
 * @date 2019-07-07 15:36
 */
public interface BaseInfo {
    String RABBITMQ_HOST_IP = "192.168.254.125";
    int RABBITMQ_HOST_PORT = 5672;
    String RABBITMQ_VIRTUAL_HOST = "/";
    String RABBITMQ_RETURN_EXCHANGE = "return-exchange";
    String RABBITMQ_RETURN_ROUTING_KEY_PRODUCER = "return.save";
    String RABBITMQ_RETURN_ROUTING_KEY_ERROR = "error.save";
    String RABBITMQ_RETURN_ROUTING_KEY_CONSUMER = "return.#";
    String RABBITMQ_RETURN_QUEUE = "return-queue";
}
