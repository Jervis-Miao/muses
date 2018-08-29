/*
 * Copyright 2010 Focus Technology, Co., Ltd. All rights reserved.
 */
package com.utils.codec;

/**
 * 摘要异常
 * 
 * @author miaoqiang
 * @since 2010-09-17
 */
public class DigestedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -508374984702163150L;

	/**
	 * 创建一个异常。
	 */
	public DigestedException() {
		super();
	}

	/**
	 * 创建一个异常。
	 * 
	 * @param message 异常信息
	 */
	public DigestedException(String message) {
		super(message);
	}

	/**
	 * 创建一个异常。
	 * 
	 * @param message 异常信息
	 * @param cause 异常原因
	 */
	public DigestedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 创建一个异常。
	 * 
	 * @param cause 异常原因
	 */
	public DigestedException(Throwable cause) {
		super(cause);
	}

}
