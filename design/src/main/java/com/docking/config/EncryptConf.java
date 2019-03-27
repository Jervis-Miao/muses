/*
Copyright 2018 All rights reserved.
 */

package com.docking.config;

import java.io.Serializable;

/**
 * @author miaoqiang
 * @date 2019/3/27.
 */
public class EncryptConf implements Serializable {
	private static final long	serialVersionUID	= -8946908219775059921L;

	/**
	 * 加密算法类名
	 */
	private String				enClassName;

	/**
	 * 加密算法key
	 */
	private String				enPriKey;

	public String getEnClassName() {
		return enClassName;
	}

	public void setEnClassName(String enClassName) {
		this.enClassName = enClassName;
	}

	public String getEnPriKey() {
		return enPriKey;
	}

	public void setEnPriKey(String enPriKey) {
		this.enPriKey = enPriKey;
	}
}
