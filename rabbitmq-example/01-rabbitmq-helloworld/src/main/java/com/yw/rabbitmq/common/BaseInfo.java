package com.yw.rabbitmq.common;

/**
 * @author yangwei
 * @date 2019-07-07 15:36
 */
public interface BaseInfo {
    String RABBITMQ_HOST_IP = "192.168.254.125";
    int RABBITMQ_HOST_PORT = 5672;
    String RABBITMQ_VIRTUAL_HOST = "/";
    String RABBITMQ_FIRST_EXCHANGE = "first-exchange";
    String RABBITMQ_FIRST_ROUTING_KEY = "first-msg";
    String RABBITMQ_FIRST_QUEUE_NAME = "first-queue";

    String RABBITMQ_SECOND_EXCHANGE = "second-exchange";
    String RABBITMQ_SECOND_ROUTING_KEY = "second-msg";
}
