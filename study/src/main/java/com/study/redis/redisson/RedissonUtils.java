/*
Copyright 2018 All rights reserved.
 */

package com.study.redis.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * redisson客户端，单例
 * @author miaoqiang
 * @date 2019/3/18.
 */
public class RedissonUtils {

	private static final Config			config;

	private static final RedissonUtils	redissonUtils;

	private RedissonClient				redissonClient;

	static {
		config = new Config();
		redissonUtils = new RedissonUtils();
	}

	private RedissonUtils() {
		super();
		this.redissonClient = Redisson.create(config);
	}

	public static RedissonUtils getInstance() {
		return redissonUtils;
	}

	public RedissonClient getRedissonClient() {
		return this.redissonClient;
	}

	/**
	 * 关闭连接，慎用
	 */
	public void shutdown() {
		this.redissonClient.shutdown();
	}
}
