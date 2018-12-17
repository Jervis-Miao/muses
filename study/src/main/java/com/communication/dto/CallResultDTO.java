/*
Copyright 2018 All rights reserved.
 */

package com.communication.dto;

/**
 * @author miaoqiang
 * @date 2018/12/13.
 */
public class CallResultDTO {
	private Long	resultId;
	private Long	callId;
	private Integer	callStatus;
	private Long	serialNo;
	private String	callResult;

	public Long getResultId() {
		return resultId;
	}

	public void setResultId(Long resultId) {
		this.resultId = resultId;
	}

	public Long getCallId() {
		return callId;
	}

	public void setCallId(Long callId) {
		this.callId = callId;
	}

	public Integer getCallStatus() {
		return callStatus;
	}

	public void setCallStatus(Integer callStatus) {
		this.callStatus = callStatus;
	}

	public Long getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Long serialNo) {
		this.serialNo = serialNo;
	}

	public String getCallResult() {
		return callResult;
	}

	public void setCallResult(String callResult) {
		this.callResult = callResult;
	}
}
