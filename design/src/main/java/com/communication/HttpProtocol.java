/*
Copyright 2018 All rights reserved.
 */

package com.communication;

import com.communication.dto.SslDTO;

/**
 * @author miaoqiang
 * @date 2018/12/12.
 */
public class HttpProtocol extends AbstractProtocol {

	/**
	 * 加载证书
	 */
	private void registerSSLSocketFactory(SslDTO ssl) {

	}

	/**
	 *
	 * @param encryptReqMsg
	 * @return
	 */
	@Override
	public String send(String encryptReqMsg, String url) {
		return "";
	}
}
