/*
 * Copyright 2010 Focus Technology, Co., Ltd. All rights reserved.
 */
package com.utils.codec.encrypter;

/**
 * 对称加密抽象工厂
 * 
 * @author miaoqiang
 */
public interface EncrypterFactory {
	enum TYPE {
		PBEWithMD5AndDES,
		PBEWithMD5AndTripleDES
		,InsCookie
	}

	public Encrypter getEncrypter(String algorithm);

}
