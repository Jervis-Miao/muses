/*
Copyright 2018 All rights reserved.
 */

package com.communication;

import com.communication.dto.ParamDTO;

import java.util.List;

/**
 * 通信协议
 * 
 * @author miaoqiang
 * @date 2018/12/12.
 */
public abstract class AbstractProtocol {

	public String getUrl(String domain, List<ParamDTO> params, String encryptReqMsg) {
		return "";
	}

	/**
	 * 发送请求
	 * 
	 * @param encryptReqMsg
	 * @return
	 */
	public abstract String send(String encryptReqMsg, String url);
}
