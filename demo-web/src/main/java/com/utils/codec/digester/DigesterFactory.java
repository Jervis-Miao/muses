/*
 * Copyright 2010 Focus Technology, Co., Ltd. All rights reserved.
 */
package com.utils.codec.digester;

/**
 * 摘要抽象工厂
 * 
 * @author miaoqiang
 */
public interface DigesterFactory {
	enum TYPE {
		MD5,
		SHA256
	}

	public Digester getDigester(String algorithm);

}
