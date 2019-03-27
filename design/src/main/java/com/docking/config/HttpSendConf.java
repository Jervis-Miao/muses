/*
Copyright 2018 All rights reserved.
 */

package com.docking.config;

import java.io.Serializable;

/**
 * @author miaoqiang
 * @date 2019/3/26.
 */
public class HttpSendConf extends SendConf implements Serializable {
	private static final long	serialVersionUID	= 7053717518971312366L;

	/**
	 * 证书信息，可为空
	 */
	private SSLConf ssl;

	public SSLConf getSsl() {
		return ssl;
	}

	public void setSsl(SSLConf ssl) {
		this.ssl = ssl;
	}
}
