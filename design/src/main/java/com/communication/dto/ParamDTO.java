/*
Copyright 2018 All rights reserved.
 */

package com.communication.dto;

/**
 * @author miaoqiang
 * @date 2018/12/13.
 */
public class ParamDTO {
	private Long			paramId;
	private Long			protocolId;
	private String			encryptMsg;
	private Long			encryptId;
	private EncryptInfoDTO	encryptInfo;

	public Long getParamId() {
		return paramId;
	}

	public void setParamId(Long paramId) {
		this.paramId = paramId;
	}

	public Long getProtocolId() {
		return protocolId;
	}

	public void setProtocolId(Long protocolId) {
		this.protocolId = protocolId;
	}

	public String getEncryptMsg() {
		return encryptMsg;
	}

	public void setEncryptMsg(String encryptMsg) {
		this.encryptMsg = encryptMsg;
	}

	public Long getEncryptId() {
		return encryptId;
	}

	public void setEncryptId(Long encryptId) {
		this.encryptId = encryptId;
	}

	public EncryptInfoDTO getEncryptInfo() {
		return encryptInfo;
	}

	public void setEncryptInfo(EncryptInfoDTO encryptInfo) {
		this.encryptInfo = encryptInfo;
	}
}
