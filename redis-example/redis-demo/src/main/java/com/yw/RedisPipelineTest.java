package com.yw;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * @author yangwei
 * @date 2020-08-29 14:13
 */
public class RedisPipelineTest {
    /**
     * 测试使用pipeline批量操作
     */
    @Test
    public void testPipeline() {
        Jedis jedis = new Jedis("192.168.254.128", 6379);
        long startTime = System.currentTimeMillis();
        Pipeline pipe = jedis.pipelined();
        for (int i = 0; i < 1000; i++) {
            pipe.set("key" + i, "val" + i);
            pipe.del("key" + i);
        }
        pipe.sync();
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("执行耗时：%d ms", endTime - startTime));
    }
    /**
     * 测试普通的操作代码
     */
    @Test
    public void testSimple() {
        Jedis jedis = new Jedis("192.168.254.128", 6379);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            jedis.set("key" + i, "val" + i);
            jedis.del("key" + i);
        }
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("执行耗时：%d ms", endTime - startTime));
    }
}
