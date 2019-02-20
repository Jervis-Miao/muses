/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.dto;

import java.io.Serializable;
import java.util.Map;

import com.study.docking.impl.send.AbstractHttpClientSend;
import com.study.docking.utils.AbstractHttpClientUtil;

/**
 * @author miaoqiang
 * @date 2019/1/28.
 */
public class HttpReqDTO extends BaseReqDTO implements Serializable {
	private static final long	serialVersionUID	= 488055905418153120L;

	/**
	 * 接口编码
	 */
	private String				code;

	/**
	 * 证书信息
	 */
	private SSLReqDTO			ssl;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
