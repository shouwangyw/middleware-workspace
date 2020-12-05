package com.yw.rabbitmq.common;

/**
 * @author yangwei
 * @date 2019-07-07 15:36
 */
public interface BaseInfo {
    String RABBITMQ_HOST_IP = "192.168.254.125";
    int RABBITMQ_HOST_PORT = 5672;
    String RABBITMQ_VIRTUAL_HOST = "/";
    String RABBITMQ_CONFIRM_EXCHANGE = "confirm-exchange";
    String RABBITMQ_CONFIRM_ROUTING_KEY_PRODUCER = "confirm.save";
    String RABBITMQ_CONFIRM_ROUTING_KEY_CONSUMER = "confirm.#";
    String RABBITMQ_CONFIRM_QUEUE = "confirm-queue";
}
