/*
 * Copyright 2010 Focus Technology, Co., Ltd. All rights reserved.
 */
package com.utils.codec.digester;

import org.apache.commons.lang.StringUtils;

/**
 * 单例模式& 工厂模式
 * 
 * @author miaoqiang
 */
public class StringDigesterFactory implements DigesterFactory {

	private static StringDigesterFactory factory = new StringDigesterFactory();

	private StringDigesterFactory() {

	}

	public static StringDigesterFactory getInstance() {
		return factory;
	}

	@Override
	public Digester getDigester(String algorithm) {
		if (StringUtils.equals(algorithm, DigesterFactory.TYPE.MD5.name())) {
			return new MD5StringDigester();
		}
		if (StringUtils.equals(algorithm, DigesterFactory.TYPE.SHA256.name())) {
			return new SHA256StringDigester();
		}
		return null;
	}
}
