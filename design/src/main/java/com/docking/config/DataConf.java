/*
Copyright 2018 All rights reserved.
 */

package com.docking.config;

import java.io.Serializable;
import java.util.List;

import com.docking.IAssembleReqBody;

/**
 * @author miaoqiang
 * @date 2019/3/26.
 */
public class DataConf implements Serializable {
	private static final long	serialVersionUID	= 4588543836583743167L;

	/**
	 * 封装请求体: 策略组件
	 * @see IAssembleReqBody 实现
	 */
	private Integer				assembleBeanType;

	private List<KVPairConf>	pairs;

	public Integer getAssembleBeanType() {
		return assembleBeanType;
	}

	public void setAssembleBeanType(Integer assembleBeanType) {
		this.assembleBeanType = assembleBeanType;
	}

	public List<KVPairConf> getPairs() {
		return pairs;
	}

	public void setPairs(List<KVPairConf> pairs) {
		this.pairs = pairs;
	}
}