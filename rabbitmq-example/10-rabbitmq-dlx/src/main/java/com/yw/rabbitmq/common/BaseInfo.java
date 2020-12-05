package com.yw.rabbitmq.common;

/**
 * @author yangwei
 * @date 2019-07-07 15:36
 */
public interface BaseInfo {
    String RABBITMQ_HOST_IP = "192.168.254.125";
    int RABBITMQ_HOST_PORT = 5672;
    String RABBITMQ_VIRTUAL_HOST = "/";
    String RABBITMQ_TEST_DLX_EXCHANGE = "test-dlx-exchange";
    String RABBITMQ_DLX_ROUTING_KEY_PRODUCER = "dlx.key";
    String RABBITMQ_DLX_ROUTING_KEY_CONSUMER = "dlx.#";
    String RABBITMQ_TEST_DLX_QUEUE = "test-dlx-queue";

    String RABBITMQ_DLX_EXCHANGE = "dlx.exchange";
    String RABBITMQ_DLX_ROUTING_KEY = "#";
    String RABBITMQ_DLX_QUEUE = "dlx.queue";
}
