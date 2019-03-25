/*
Copyright 2018 All rights reserved.
 */

package com.muses.web.service;

import com.muses.common.utils.SpringContextUtils;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.applet.AppletContext;

/**
 * @author miaoqiang
 * @date 2019/3/21.
 */
@Service
public class TopicComponent implements ApplicationContextAware {

	@Autowired
	private RedissonClient	redissonClient;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		RTopic topic = applicationContext.getBean(RedissonClient.class).getTopic("topic*");
		topic.addListener(String.class, new MessageListener<String>() {
			@Override
			public void onMessage(CharSequence channel, String msg) {
				System.out.println("setApplicationContext, " + msg);
			}
		});
	}

	@PostConstruct
	public void init() {
		RTopic topic = redissonClient.getTopic("topic*");
		topic.addListener(String.class, new MessageListener<String>() {
			@Override
			public void onMessage(CharSequence channel, String msg) {
				System.out.println("init, " + msg);
			}
		});
	}

}
