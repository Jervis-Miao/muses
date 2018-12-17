/*
Copyright 2018 All rights reserved.
 */

package com.communication.dto;

/**
 * @author miaoqiang
 * @date 2018/12/13.
 */
public class CallReqMsgDTO {
	private Long			msgId;
	private Long			callId;
	private Integer			encyptType;
	private Long			encryptId;
	private EncryptInfoDTO	encryptInfo;
	private AttInfoDTO		attInfo;

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public Long getCallId() {
		return callId;
	}

	public void setCallId(Long callId) {
		this.callId = callId;
	}

	public Integer getEncyptType() {
		return encyptType;
	}

	public void setEncyptType(Integer encyptType) {
		this.encyptType = encyptType;
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

	public AttInfoDTO getAttInfo() {
		return attInfo;
	}

	public void setAttInfo(AttInfoDTO attInfo) {
		this.attInfo = attInfo;
	}
}
