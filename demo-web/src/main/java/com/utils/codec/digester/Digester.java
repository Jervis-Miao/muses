/*
 * Copyright 2010 Focus Technology, Co., Ltd. All rights reserved.
 */
package com.utils.codec.digester;

import com.utils.codec.DigestedException;

/**
 * 摘要通用接口
 * 
 * @author miaoqiang
 * @since 2010-09-17
 * @version 1.0
 */
public interface Digester {

	public byte[] digest(byte[] message) throws DigestedException;

	public boolean matches(byte[] message, byte[] digest) throws DigestedException;

	public String digest(String message) throws DigestedException;

	public boolean matches(String message, String digest) throws DigestedException;

}
