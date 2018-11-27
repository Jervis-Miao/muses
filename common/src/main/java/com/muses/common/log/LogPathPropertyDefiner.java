/*
Copyright 2018 All rights reserved.
 */

package com.muses.common.log;

import ch.qos.logback.core.PropertyDefinerBase;
import com.muses.common.utils.PropertiesContextAndPlaceholderConfigurer;
import com.muses.common.utils.SpringContextUtils;

/**
 * @author miaoqiang
 * @date 2018/11/27.
 */
public class LogPathPropertyDefiner extends PropertyDefinerBase {
	@Override
	public String getPropertyValue() {
		PropertiesContextAndPlaceholderConfigurer bean = SpringContextUtils
				.getBean(PropertiesContextAndPlaceholderConfigurer.class);
		return bean.getProperty("log.path");
	}
}
