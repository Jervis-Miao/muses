/*
Copyright 2018 All rights reserved.
 */

package com.study.redis.redisson;

import org.reactivestreams.Publisher;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RBucketReactive;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.codec.SerializationCodec;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.redisson.config.SingleServerConfig;
import org.redisson.config.SubscriptionMode;

import cn.muses.api.dto.StudentDTO;

import reactor.core.publisher.Mono;

/**
 * redisson客户端，单例
 * @author miaoqiang
 * @date 2019/3/18.
 */
public class RedissonUtils {

	private static final Config			config;

	private static final RedissonUtils	redissonUtils;

	private RedissonClient				redissonClient;

	private RedissonReactiveClient		redissonReactiveClient;

	static {
		config = new Config();
		useSentinelServers();
		redissonUtils = new RedissonUtils();
	}

	private RedissonUtils() {
		super();
		// 创建客户端(发现创建RedissonClient非常耗时，基本在2秒-4秒左右)
		this.redissonClient = Redisson.create(config);
		this.redissonReactiveClient = Redisson.createReactive(config);
	}

	public static RedissonUtils getInstance() {
		return redissonUtils;
	}

	/**
	 * 集群模式
	 */
	private static void useSentinelServers() {
		// 默认JDK序列化方式
		config.setCodec(new SerializationCodec());
		// 集群配置
		config.useSentinelServers()
				.setMasterName("low_reliability")
				.setReadMode(ReadMode.MASTER)
				.setSubscriptionMode(SubscriptionMode.MASTER)
				.setSlaveConnectionMinimumIdleSize(0)
				.setSlaveConnectionPoolSize(0)
				.setMasterConnectionMinimumIdleSize(10)
				.setMasterConnectionPoolSize(64)
				.setIdleConnectionTimeout(10 * 1000)
				.setConnectTimeout(10 * 1000)
				.setTimeout(3 * 1000)
				.setRetryAttempts(3)
				.setRetryInterval(500)
				.setFailedSlaveReconnectionInterval(3 * 1000)
				.setFailedSlaveCheckInterval(60 * 1000)
				.setDatabase(15)
				.setClientName("muses-test")
				.addSentinelAddress(
						new String[] { "redis://192.168.56.89:26379", "redis://192.168.56.90:26379",
								"redis://192.168.58.101:26379" });
	}

	/**
	 * 单点模式
	 */
	private static void useSingleServer() {
		// 默认JDK序列化方式
		config.setCodec(new SerializationCodec());
		// 指定使用单节点部署方式
		SingleServerConfig singleServerConfig = config.useSingleServer();
		// redis地址
		singleServerConfig.setAddress("redis://192.168.50.33:6379");
		// 设置密码
		singleServerConfig.setPassword("");
		// 设置对于master节点的连接池中连接数最大为500
		singleServerConfig.setConnectionPoolSize(500);
		// 如果当前连接池里的连接数量超过了最小空闲连接数，而同时有连接空闲时间超过了该数值，那么这些连接将会自动被关闭，并从连接池里去掉。时间单位是毫秒。
		singleServerConfig.setIdleConnectionTimeout(10 * 1000);
		// 同任何节点建立连接时的等待超时。时间单位是毫秒。
		singleServerConfig.setConnectTimeout(30 * 1000);
		// 等待节点回复命令的时间。该时间从命令发送成功时开始计时。
		singleServerConfig.setTimeout(3 * 1000);
		singleServerConfig.setPingTimeout(30 * 1000);
		// 当与某个节点的连接断开时，等待与其重新建立连接的时间间隔。时间单位是毫秒。
		singleServerConfig.setReconnectionTimeout(3 * 1000);
		// redis库
		singleServerConfig.setDatabase(15);
	}

	public RedissonClient getRedissonClient() {
		return this.redissonClient;
	}

	public RedissonReactiveClient getRedissonReactiveClient() {
		return redissonReactiveClient;
	}

	/**
	 * 关闭连接，慎用
	 */
	private void shutdown() {
		this.redissonClient.shutdown();
		this.redissonReactiveClient.shutdown();
	}

	private void test1() {
		StudentDTO studentDTO = new StudentDTO(1L, "jervis");
		System.out.println(studentDTO);

		RedissonClient redissonClient = RedissonUtils.getInstance().getRedissonClient();
		RBucket<StudentDTO> bucket = redissonClient.getBucket("redissonClient", new JsonJacksonCodec());
		bucket.set(studentDTO);

		RedissonReactiveClient redissonReactiveClient = RedissonUtils.getInstance().getRedissonReactiveClient();
		RBucketReactive<StudentDTO> bucketR = redissonReactiveClient.getBucket("redissonReactiveClient",
				new JsonJacksonCodec());
		Mono<Void> set = bucketR.set(studentDTO);
		Mono.from(set).block();
		Publisher<StudentDTO> get = bucketR.get();
		StudentDTO studentDTO1 = Mono.from(get).block();
		System.out.println(studentDTO1);
		System.out.println(studentDTO1.equals(studentDTO));
	}

	private void test2() {
		RTopic topic = RedissonUtils.getInstance().getRedissonClient().getTopic("topic*");
		topic.publish("2");
	}

	public static void main(String[] args) {
		RedissonUtils.getInstance().test2();
		RedissonUtils.getInstance().shutdown();
	}
}
