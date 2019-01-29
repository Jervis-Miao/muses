/*
Copyright 2018 All rights reserved.
 */

package com.study.docking;

/**
 * 发送请求报文
 * 
 * @author miaoqiang
 * @date 2019/1/16.
 */
public interface ISendReqMsg<T> {

	/**
	 * 发送请求获取返回报文
	 * 
	 * @param reqMsg
	 * @return
	 */
	public T sendReqMsg(T reqMsg);

	/**
	 * 发送请求获取返回报文字节流
	 * 
	 * @param reqMsg
	 * @return
	 */
	public byte[] sendReqMsgForByte(T reqMsg);
}
