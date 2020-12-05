package com.yw;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.JedisCluster;

/**
 * @author yangwei
 * @date 2020-08-27 21:35
 */
public class RedisSpringTest {
    private ApplicationContext applicationContext;
    @Before
    public void init(){
        applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    }
    // redis集群
    @Test
    public void testJedisCluster(){
        JedisCluster cluster = (JedisCluster)applicationContext.getBean("jedisCluster");
        cluster.set("name", "zhangsan");
        String value = cluster.get("name");
        System.out.println(value);
    }
}
