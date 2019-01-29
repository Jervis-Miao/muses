/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * @author miaoqiang
 * @date 2019/1/16.
 */
public class DockingReqDTO implements Serializable {
	private static final long	serialVersionUID	= -3474791966730094003L;

	/**
	 * 接口编码
	 */
	private String				code;

	/**
	 * 扩展变量
	 */
	private Map<String, Object>	extendParams;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Map<String, Object> getExtendParams() {
		return extendParams;
	}

	public void setExtendParams(Map<String, Object> extendParams) {
		this.extendParams = extendParams;
	}
}
