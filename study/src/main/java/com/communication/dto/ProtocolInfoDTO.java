/*
Copyright 2018 All rights reserved.
 */

package com.communication.dto;

import java.util.List;

/**
 * @author miaoqiang
 * @date 2018/12/13.
 */
public class ProtocolInfoDTO {
	private Long			protocolId;
	private String			beanName;
	private String			domain;
	private SslDTO			sslDTO;
	private List<ParamDTO>	params;

	public Long getProtocolId() {
		return protocolId;
	}

	public void setProtocolId(Long protocolId) {
		this.protocolId = protocolId;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public SslDTO getSslDTO() {
		return sslDTO;
	}

	public void setSslDTO(SslDTO sslDTO) {
		this.sslDTO = sslDTO;
	}

	public List<ParamDTO> getParams() {
		return params;
	}

	public void setParams(List<ParamDTO> params) {
		this.params = params;
	}
}
