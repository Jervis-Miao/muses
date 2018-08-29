/*
 * Copyright 2010 Focus Technology, Co., Ltd. All rights reserved.
 */
package com.utils.codec.encrypter;

import org.apache.commons.lang.StringUtils;

/**
 * 单例模式& 工厂模式
 * 
 * @author miaoqiang
 */
public class StringEncrypterFactory implements EncrypterFactory {

	private static StringEncrypterFactory factory = new StringEncrypterFactory();

	public static StringEncrypterFactory getInstance() {
		return factory;
	}

	private StringEncrypterFactory() {

	}

	@Override
	public Encrypter getEncrypter(String algorithm) {
		if (StringUtils.equals(algorithm, EncrypterFactory.TYPE.PBEWithMD5AndDES.name())) {
			return new DESStringEncrypter();
		}
		if (StringUtils.equals(algorithm, EncrypterFactory.TYPE.PBEWithMD5AndTripleDES.name())) {
			return new TriDESStringEncrypter();
		}
		if (StringUtils.equals(algorithm, EncrypterFactory.TYPE.InsCookie.name())) {
			return new InsCookieEncrypter();
		}
		return null;
	}
}
