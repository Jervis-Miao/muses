/*
Copyright 2018 All rights reserved.
 */

package com.docking.config;

import java.io.Serializable;
import java.util.List;

/**
 * @author miaoqiang
 * @date 2019/3/26.
 */
public class UrlConf implements Serializable {
	private static final long	serialVersionUID	= 3639772612648553377L;

	/**
	 * 请求地址类型
	 */
	private Integer				type;

	/**
	 * <pre>
	 * type:0, 静态地址
	 * type:1, 关联接口编码
	 * </pre>
	 */
	private String				info;

	/**
	 * 动态参数
	 */
	private List<KVPair>		params;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public List<KVPair> getParams() {
		return params;
	}

	public void setParams(List<KVPair> params) {
		this.params = params;
	}
}
