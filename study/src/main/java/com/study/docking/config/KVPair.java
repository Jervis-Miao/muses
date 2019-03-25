/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.config;

import java.io.Serializable;

/**
 * @author miaoqiang
 * @date 2019/3/25.
 */
public class KVPair implements Serializable {
	private static final long	serialVersionUID	= -4617095634429045103L;

	private String				key;

	private String				encodeClassName;

	private String				encodeKey;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getEncodeClassName() {
		return encodeClassName;
	}

	public void setEncodeClassName(String encodeClassName) {
		this.encodeClassName = encodeClassName;
	}

	public String getEncodeKey() {
		return encodeKey;
	}

	public void setEncodeKey(String encodeKey) {
		this.encodeKey = encodeKey;
	}
}
