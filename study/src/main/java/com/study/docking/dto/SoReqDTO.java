/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.dto;

import java.io.Serializable;

/**
 * @author miaoqiang
 * @date 2019/2/21.
 */
public class SoReqDTO extends BaseReqDTO implements Serializable {
	private static final long	serialVersionUID	= -3867447808113415375L;

	private Integer				urlPort;

	public Integer getUrlPort() {
		return urlPort;
	}

	public void setUrlPort(Integer urlPort) {
		this.urlPort = urlPort;
	}
}
