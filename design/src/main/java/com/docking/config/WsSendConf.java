/*
Copyright 2018 All rights reserved.
 */

package com.docking.config;

import java.io.Serializable;

/**
 * @author miaoqiang
 * @date 2019/3/26.
 */
public class WsSendConf extends SendConf implements Serializable {
	private static final long	serialVersionUID	= 2120708718020332094L;

	/**
	 * 命名空间
	 */
	private String				nameSpace;

	/**
	 * 方法名
	 */
	private String				interFace;

	public String getNameSpace() {
		return nameSpace;
	}

	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

	public String getInterFace() {
		return interFace;
	}

	public void setInterFace(String interFace) {
		this.interFace = interFace;
	}
}
