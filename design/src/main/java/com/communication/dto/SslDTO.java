/*
Copyright 2018 All rights reserved.
 */

package com.communication.dto;

/**
 * @author miaoqiang
 * @date 2018/12/13.
 */
public class SslDTO {
	private Long		sslId;
	private Long		protocolId;
	private AttInfoDTO	attInfo;

	public Long getSslId() {
		return sslId;
	}

	public void setSslId(Long sslId) {
		this.sslId = sslId;
	}

	public Long getProtocolId() {
		return protocolId;
	}

	public void setProtocolId(Long protocolId) {
		this.protocolId = protocolId;
	}

	public AttInfoDTO getAttInfo() {
		return attInfo;
	}

	public void setAttInfo(AttInfoDTO attInfo) {
		this.attInfo = attInfo;
	}
}
