/*
Copyright 2018 All rights reserved.
 */

package com.docking.config;

import java.io.Serializable;

/**
 * @author miaoqiang
 * @date 2019/3/25.
 */
public class KVPairConf implements Serializable {
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
	 * 报文模板
	 */
	private TemplateConf		templateConf;

	/**
	 * 转换对象类名
	 */
	private String				className;

	/**
	 * 转换对象jar文件
	 */
	private Long				fileId;

	/**
	 * 加密算法
	 */
	private EncryptConf			encryptConf;

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

	public TemplateConf getTemplateConf() {
		return templateConf;
	}

	public void setTemplateConf(TemplateConf templateConf) {
		this.templateConf = templateConf;
	}

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

	public EncryptConf getEncryptConf() {
		return encryptConf;
	}

	public void setEncryptConf(EncryptConf encryptConf) {
		this.encryptConf = encryptConf;
	}
}
