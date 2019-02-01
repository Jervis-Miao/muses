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
	 * 系统信息传输
	 * 
	 * @param reqMsg
	 * @return
	 */
	public T send(T reqMsg);

	/**
	 * 文件下载
	 * 
	 * @param reqMsg
	 * @return
	 */
	public byte[] sendForByte(T reqMsg);
}
