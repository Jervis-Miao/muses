/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.call.processor.send;

/**
 * 发送请求
 * 
 * @author miaoqiang
 * @date 2018/9/7.
 */
public interface SendRequestProcessor {

	/**
	 * 发送请求
	 * 
	 * @param message
	 * @return
	 */
	public String send(String message);
}
