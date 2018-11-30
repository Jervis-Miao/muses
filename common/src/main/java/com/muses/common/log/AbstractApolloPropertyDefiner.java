/*
Copyright 2018 All rights reserved.
 */

package com.muses.common.log;

import ch.qos.logback.core.PropertyDefinerBase;
import com.ctrip.framework.apollo.ConfigService;

/**
 * 获取apollo配置中心变量
 * 
 * @author Jervis
 * @date 2018/11/27.
 */
public class AbstractApolloPropertyDefiner extends PropertyDefinerBase {
	private String	namespace;

	private String	keyName;

	@Override
	public String getPropertyValue() {
		return ConfigService.getConfig(namespace).getProperty(keyName, "${" + keyName + "}");
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
}
