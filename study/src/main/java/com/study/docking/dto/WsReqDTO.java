/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.dto;

import java.io.Serializable;

/**
 * @author miaoqiang
 * @date 2019/2/12.
 */
public class WsReqDTO extends BaseReqDTO implements Serializable {
	private static final long	serialVersionUID	= 7505954373585097608L;

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
