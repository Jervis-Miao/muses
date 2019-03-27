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

	/**
	 * 键值对定义value键值
	 */
	private String				defValueKey;

	/**
	 * 键值对获取value键值
	 */
	private String				obtainValKey;

	/**
	 * 加密算法类
	 */
	private String				enClassName;

	/**
	 * 加密算法私钥
	 */
	private String				enPriKey;

	public String getDefValueKey() {
		return defValueKey;
	}

	public void setDefValueKey(String defValueKey) {
		this.defValueKey = defValueKey;
	}

	public String getObtainValKey() {
		return obtainValKey;
	}

	public void setObtainValKey(String obtainValKey) {
		this.obtainValKey = obtainValKey;
	}

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
