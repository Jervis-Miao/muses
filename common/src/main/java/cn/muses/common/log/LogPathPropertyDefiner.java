/*
Copyright 2018 All rights reserved.
 */

package cn.muses.common.log;

import cn.muses.common.utils.PropertiesContext;
import cn.muses.common.utils.SpringContextUtils;

import ch.qos.logback.core.PropertyDefinerBase;

/**
 * @author Jervis
 * @date 2018/11/27.
 */
public class LogPathPropertyDefiner extends PropertyDefinerBase {
	@Override
	public String getPropertyValue() {
		PropertiesContext bean = SpringContextUtils.getBean(PropertiesContext.class);
		return bean.getProperty("log.path");
	}
}
