/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.config;

import com.study.docking.IAssembleReqBody;

import java.io.Serializable;

/**
 * @author miaoqiang
 * @date 2019/3/26.
 */
public class DataConf implements Serializable {
	private static final long	serialVersionUID	= 4588543836583743167L;

	/**
	 * 报文封装: 策略组件
	 * @see IAssembleReqBody 实现
	 */
	private Integer				assembleBeanType;

	public Integer getAssembleBeanType() {
		return assembleBeanType;
	}

	public void setAssembleBeanType(Integer assembleBeanType) {
		this.assembleBeanType = assembleBeanType;
	}
}