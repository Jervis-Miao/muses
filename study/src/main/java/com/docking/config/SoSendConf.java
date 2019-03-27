/*
Copyright 2018 All rights reserved.
 */

package com.docking.config;

import java.io.Serializable;

/**
 * @author miaoqiang
 * @date 2019/3/26.
 */
public class SoSendConf extends SendConf implements Serializable {
	private static final long	serialVersionUID	= 7868674099194037943L;

	private Integer				urlPort;

	public Integer getUrlPort() {
		return urlPort;
	}

	public void setUrlPort(Integer urlPort) {
		this.urlPort = urlPort;
	}
}
