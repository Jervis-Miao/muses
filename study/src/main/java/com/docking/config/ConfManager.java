/*
Copyright 2018 All rights reserved.
 */

package com.docking.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.SerializationUtils;

/**
 * 执行器配置管理器
 * 
 * @author miaoqiang
 * @date 2019/3/22.
 */
public class ConfManager {

	private static final Map<String, Config>	CONFIGS	= new ConcurrentHashMap<>();

	private ConfManager() {
		super();
	}

	public static ConfManager getInstance() {
		return ConfManagerFactory.confManager;
	}

	private static class ConfManagerFactory {
		private static final ConfManager	confManager	= new ConfManager();
	}

	/**
	 * 新建配置
	 * 
	 * @param code
	 * @return
	 */
	private static Config create(String code) {
		return new Config();
	}

	/**
	 * 新建配置
	 *
	 * @param config
	 * @return
	 */
	private static Config create(Config config) {
		return config;
	}

	/**
	 * 加载配置信息
	 */
	@PostConstruct
	public void loadConfigs() {
		List<String> codes = new ArrayList<>();
		codes.forEach(c -> CONFIGS.put(c, create(c)));
	}

	/**
	 * 更新执行器配置
	 * 
	 * @param code
	 */
	public static void update(String code) {
		Config config = CONFIGS.get(code);
		if (config.getUpFlag().compareAndSet(Boolean.FALSE, Boolean.TRUE)) {
			try {
				CONFIGS.put(code, create(config));
			} finally {
				config.getUpFlag().set(Boolean.FALSE);
			}
		}
	}

	/**
	 * 获取执行器配置信息
	 * 
	 * @param code
	 * @return
	 * @throws InterruptedException
	 */
	public static Config get(String code) {
		Config config = CONFIGS.get(code);
		while (config.getUpFlag().get()) {
			// 等待配置更新
		}
		return (Config) SerializationUtils.clone((Serializable) config);
	}

}
