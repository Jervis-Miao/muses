/*
Copyright 2018 All rights reserved.
 */

package com.communication.dto;

/**
 * @author miaoqiang
 * @date 2018/12/13.
 */
public class EncryptInfoDTO {
	private Long	encryptId;
	private String	encryptBeanName;

	public Long getEncryptId() {
		return encryptId;
	}

	public void setEncryptId(Long encryptId) {
		this.encryptId = encryptId;
	}

	public String getEncryptBeanName() {
		return encryptBeanName;
	}

	public void setEncryptBeanName(String encryptBeanName) {
		this.encryptBeanName = encryptBeanName;
	}
}
