/*
Copyright 2018 All rights reserved.
 */

package com.study.docking;

import com.study.docking.dto.BaseReqDTO;
import com.study.docking.dto.DockingReqDTO;

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
	 * @param reqDTO
	 * @return
	 */
	public T send(T reqMsg, DockingReqDTO reqDTO);

	/**
	 * 文件下载
	 * 
	 * @param reqMsg
	 * @param reqDTO
	 * @return
	 */
	public byte[] sendForByte(T reqMsg, DockingReqDTO reqDTO);

	/**
	 * 封装协议发送请求参数
	 * 
	 * @param reqMsg
	 * @param reqDTO
	 * @return
	 */
	public BaseReqDTO assembleReqDTO(T reqMsg, DockingReqDTO reqDTO);
}
