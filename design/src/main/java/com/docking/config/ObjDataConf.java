/*
Copyright 2018 All rights reserved.
 */

package com.docking.config;

import java.io.Serializable;

/**
 * @author miaoqiang
 * @date 2019/3/27.
 */
public class ObjDataConf extends StrDataConf implements Serializable {
	private static final long	serialVersionUID	= 8854355958806405026L;

	private String				className;

	private Long				fileId;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
}
