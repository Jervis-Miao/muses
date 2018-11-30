/*
Copyright 2018 All rights reserved.
 */

package com.muses.common.log;

import ch.qos.logback.core.PropertyDefinerBase;

/**
 * @author Jervis
 * @date 2018/11/27.
 */
public class FilePropertyDefiner extends PropertyDefinerBase {

	@Override
	public String getPropertyValue() {
		String currentProfile = System.getProperty("spring.profiles.active", "development");
		return "properties/logback-" + currentProfile + ".properties";
	}
}
