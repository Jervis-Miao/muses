/*
Copyright 2018 All rights reserved.
 */

package com.communication.dto;

import java.util.Map;

/**
 * @author miaoqiang
 * @date 2018/12/13.
 */
public class CallInfoDTO {
	private Long						callId;
	private Long						confId;
	private Map<Integer, CallReqMsgDTO>	callReqMsgs;
	private CallResMsgDTO				callResMsg;
	private ProtocolInfoDTO				protocolInfo;

	public Long getCallId() {
		return callId;
	}

	public void setCallId(Long callId) {
		this.callId = callId;
	}

	public Long getConfId() {
		return confId;
	}

	public void setConfId(Long confId) {
		this.confId = confId;
	}

	public Map<Integer, CallReqMsgDTO> getCallReqMsgs() {
		return callReqMsgs;
	}

	public void setCallReqMsgs(Map<Integer, CallReqMsgDTO> callReqMsgs) {
		this.callReqMsgs = callReqMsgs;
	}

	public CallResMsgDTO getCallResMsg() {
		return callResMsg;
	}

	public void setCallResMsg(CallResMsgDTO callResMsg) {
		this.callResMsg = callResMsg;
	}

	public ProtocolInfoDTO getProtocolInfo() {
		return protocolInfo;
	}

	public void setProtocolInfo(ProtocolInfoDTO protocolInfo) {
		this.protocolInfo = protocolInfo;
	}
}
