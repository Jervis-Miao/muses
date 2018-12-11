/*
Copyright All rights reserved.
 */

package com.study.call.processor.send;

/**
 * 发送请求
 * 
 * @author Jervis
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
