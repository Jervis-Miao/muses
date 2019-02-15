/*
Copyright 2018 All rights reserved.
 */

package com.study.docking.impl.send;

import com.study.docking.ISendReqMsg;
import com.study.docking.dto.DockingReqDTO;

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
	 * @param reqDTO
	 * @return
	 */
	@Override
	public abstract T send(T reqMsg, DockingReqDTO reqDTO);

	/**
	 * 文件下载
	 *
	 * @param reqMsg
	 * @param reqDTO
	 * @return
	 */
	@Override
	public abstract byte[] sendForByte(T reqMsg, DockingReqDTO reqDTO);
}
