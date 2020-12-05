package com.yw;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

/**
 * 测试redis连通性
 * @author yangwei
 */
public class RedisTest {
	@Test
	public void testJedis() throws Exception{
		Jedis jedis = new Jedis("192.168.254.128", 6379);
        System.out.println(jedis.ping());
        // 程序结束时，需要关闭Jedis对象
        jedis.close();
	}

	@Test
	public void testJedisPool() {
		JedisPool jedisPool = new JedisPool("192.168.254.128", 6379);
		Jedis jedis = jedisPool.getResource();
		jedis.set("mytest", "hello world, this is jedis client");
		String result = jedis.get("mytest");
		System.out.println(result);
		jedis.close();
		jedisPool.close();
	}

	@Test
	public void testJedisCluster() throws Exception{
		// 创建一个连接，JedisCluster对象在系统中是单例存在
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.254.128", 7001));
		nodes.add(new HostAndPort("192.168.254.128", 7002));
		nodes.add(new HostAndPort("192.168.254.128", 7003));
		nodes.add(new HostAndPort("192.168.254.128", 7004));
		nodes.add(new HostAndPort("192.168.254.128", 7005));
		nodes.add(new HostAndPort("192.168.254.128", 7006));
		JedisCluster cluster = new JedisCluster(nodes);
		// 执行JedisCluster对象中的方法，方法与redis一一对应
		cluster.set("cluster-test", "my jedis cluster test");
		String result = cluster.get("cluster-test");
		System.out.println(result);
		// 程序结束时，需要关闭JedisCluster对象
		cluster.close();
	}
}