/*
Copyright 2018 All rights reserved.
 */

package com.communication.dto;

import java.util.List;

/**
 * @author miaoqiang
 * @date 2018/12/13.
 */
public class CallCombinationDTO {
	private Long			confId;
	private List<CallInfoDTO>	callInfos;

	public Long getConfId() {
		return confId;
	}

	public void setConfId(Long confId) {
		this.confId = confId;
	}

	public List<CallInfoDTO> getCallInfos() {
		return callInfos;
	}

	public void setCallInfos(List<CallInfoDTO> callInfos) {
		this.callInfos = callInfos;
	}
}
