/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.call.processor.security;

/**
 * 安全管家
 * 
 * @author miaoqiang
 * @date 2018/9/7.
 */
public interface SecurityManagerProcessor {

	/**
	 * 加密请求报文
	 * 
	 * @param reqMessage
	 * @return
	 */
	public String encrypt(String reqMessage);

	/**
	 * 解密返回报文
	 * 
	 * @param resMessage
	 * @return
	 */
	public String decrypt(String resMessage);
}
