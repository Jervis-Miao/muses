/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.config;

import javax.annotation.PostConstruct;

import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author miaoqiang
 * @date 2019/3/21.
 */
@Component
public class ConfRefresh {
	@Autowired
	private RedissonClient		redissonClient;
	private static final String	UPDATE_CONFIG_TOPIC	= "UPDATE_CONFIG_TOPIC";

	/**
	 * 注册配置更新订阅消息
	 */
	@PostConstruct
	private void registrySub() {
		RTopic configTopic = redissonClient.getTopic(UPDATE_CONFIG_TOPIC);
		configTopic.addListener(String.class, new MessageListener<String>() {
			@Override
			public void onMessage(CharSequence channel, String msg) {
				ConfManager.update(msg);
			}
		});
	}

	/**
	 * 发布接口更新消息
	 * 
	 * @param code
	 */
	public void pub(String code) {
		RTopic configTopic = redissonClient.getTopic(UPDATE_CONFIG_TOPIC);
		configTopic.publish(code);
	}
}
