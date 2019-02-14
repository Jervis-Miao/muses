/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.impl.send;

import com.study.docking.ISendReqMsg;

/**
 * 保险公司定制协议发送报文
 * 
 * @author miaoqiang
 * @date 2019/2/12.
 */
public abstract class AbstractCustomSend<T> implements ISendReqMsg<T> {
	/**
	 * 系统信息传输
	 *
	 * @param reqMsg
	 * @return
	 */
	@Override
	public abstract T send(T reqMsg);

	/**
	 * 文件下载
	 *
	 * @param reqMsg
	 * @return
	 */
	@Override
	public abstract byte[] sendForByte(T reqMsg);
}
