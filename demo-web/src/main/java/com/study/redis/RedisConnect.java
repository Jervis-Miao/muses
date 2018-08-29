/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.study.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author miaoqiang
 * @date 2018/8/22.
 */
public class RedisConnect {
	/**
	 * 连接配置
	 */
	private static final Config	config	= new Config();

	/**
	 * 客户端
	 */
	private RedissonClient		redisson;

	static {
		// 指定编码，默认编码为org.redisson.codec.JsonJacksonCodec
		// 之前使用的spring-data-redis，用的客户端jedis，编码为org.springframework.data.redis.serializer.StringRedisSerializer
		// 改用redisson后为了之间数据能兼容，这里修改编码为org.redisson.client.codec.StringCodec
		// config.setCodec(new org.redisson.client.codec.StringCodec());
		config.setCodec(new org.redisson.codec.FstCodec());

		// 指定使用单节点部署方式
		config.useSingleServer().setAddress("redis://192.168.50.33:6379");
		// 设置密码
		// config.useSingleServer().setPassword("password");
		// 设置对于master节点的连接池中连接数最大为500
		config.useSingleServer().setConnectionPoolSize(500);
		// 如果当前连接池里的连接数量超过了最小空闲连接数，而同时有连接空闲时间超过了该数值，那么这些连接将会自动被关闭，并从连接池里去掉。时间单位是毫秒。
		config.useSingleServer().setIdleConnectionTimeout(10000);
		// 同任何节点建立连接时的等待超时。时间单位是毫秒。
		config.useSingleServer().setConnectTimeout(30000);
		// 等待节点回复命令的时间。该时间从命令发送成功时开始计时。
		config.useSingleServer().setTimeout(3000);
		config.useSingleServer().setPingTimeout(30000);
		// 当与某个节点的连接断开时，等待与其重新建立连接的时间间隔。时间单位是毫秒。
		config.useSingleServer().setReconnectionTimeout(3000);

	}

	RedisConnect() {
		// 创建客户端(发现创建RedissonClient非常耗时，基本在2秒-4秒左右)
		this.redisson = Redisson.create(config);
	}

	public RedissonClient getRedisson() {
		return redisson;
	}

	/**
	 * 关闭连接，慎用
	 */
	public void shutdown() {
		this.redisson.shutdown();
	}
}
