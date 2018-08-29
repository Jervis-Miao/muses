/*
 * Copyright 2010 Focus Technology, Co., Ltd. All rights reserved.
 */
package com.utils.codec;

/**
 * 加密异常。
 * 
 * @author miaoqiang
 * @version 1.0
 * @since 2010-09-17
 */

public class EncryptException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 2951048323915423605L;

	/**
	 * 创建一个异常。
	 */
	public EncryptException() {
		super();
	}

	/**
	 * 创建一个异常。
	 * 
	 * @param message 异常信息
	 */
	public EncryptException(String message) {
		super(message);
	}

	/**
	 * 创建一个异常。
	 * 
	 * @param message 异常信息
	 * @param cause 异常原因
	 */
	public EncryptException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 创建一个异常。
	 * 
	 * @param cause 异常原因
	 */
	public EncryptException(Throwable cause) {
		super(cause);
	}
}
