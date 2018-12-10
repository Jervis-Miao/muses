/*
Copyright 2018 All rights reserved.
 */

package com.muses.common.utils;

import java.text.MessageFormat;
import java.util.Properties;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.CollectionUtils;

/**
 * @author Jervis
 * @date 2018/11/20.
 */
public class PropertiesContext implements InitializingBean {
	private Properties	property;

	@Override
	public void afterPropertiesSet() throws Exception {
		// 先从SpringContextUtils中获取, 避免对属性文件的重复加载
		try {
			PropertiesFactoryBean pf = SpringContextUtils.getBean(PropertiesFactoryBean.class);
			this.property = pf.getObject();
		} catch (Exception e) {
			// 未配置为spring bean
			String fileName = "properties/application" + ".properties";
			String currentProfile = System.getProperty("spring.profiles.active", "development");
			String contextFileName = "properties/application-" + currentProfile + ".properties";
			this.property = PropertiesLoaderUtils.loadAllProperties(fileName);
			Properties contextProperties = PropertiesLoaderUtils.loadAllProperties(contextFileName);
			CollectionUtils.mergePropertiesIntoMap(contextProperties, this.property);
		}

	}

	public String getProperty(String key) {
		return property.getProperty(key);
	}

	public String getProperty(String key, String defaultValue) {
		return property.getProperty(key, defaultValue);
	}

	public String getProperty(String key, Object[] param) {
		String str = this.getProperty(key);
		return MessageFormat.format(str, param);
	}

	public String getProperty(String key, String defaultValue, Object[] param) {
		String str = this.getProperty(key, defaultValue);
		return MessageFormat.format(str, param);
	}
}
