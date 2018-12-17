/*
Copyright 2018 All rights reserved.
 */

package com.communication.dto;

import java.util.List;

/**
 * @author miaoqiang
 * @date 2018/12/13.
 */
public class CallResMsgDTO {
	private Long					resMsgId;
	private Long					encryptId;
	private List<CallResFieldDTO>	callResFields;
	private EncryptInfoDTO			encryptInfo;

	public Long getResMsgId() {
		return resMsgId;
	}

	public void setResMsgId(Long resMsgId) {
		this.resMsgId = resMsgId;
	}

	public Long getEncryptId() {
		return encryptId;
	}

	public void setEncryptId(Long encryptId) {
		this.encryptId = encryptId;
	}

	public List<CallResFieldDTO> getCallResFields() {
		return callResFields;
	}

	public void setCallResFields(List<CallResFieldDTO> callResFields) {
		this.callResFields = callResFields;
	}

	public EncryptInfoDTO getEncryptInfo() {
		return encryptInfo;
	}

	public void setEncryptInfo(EncryptInfoDTO encryptInfo) {
		this.encryptInfo = encryptInfo;
	}
}
