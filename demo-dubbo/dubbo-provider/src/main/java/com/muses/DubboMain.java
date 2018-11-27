/*
Copyright 2018 All rights reserved.
 */
package com.muses;

import com.muses.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jervis
 * @date 2018/11/20.
 */
public class DubboMain {
	private static final Logger	LOGGER	= LoggerFactory.getLogger(DubboMain.class);

	public static void main(String[] args) throws Exception {
		if (StringUtils.isBlank(System.getProperty("spring.profiles.active"))) {
			System.setProperty("spring.profiles.active", "development");
		}

		com.alibaba.dubbo.container.Main.main(new String[0]);
	}
}
