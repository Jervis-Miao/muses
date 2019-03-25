/*
Copyright 2018 All rights reserved.
 */

package com.study.docking;

import com.study.docking.config.MsgConf;
import com.study.docking.dto.DockingReqDTO;

/**
 * 封装请求报文
 * 
 * @author miaoqiang
 * @date 2019/1/16.
 */
public interface IAssembleReqMsg {

	/**
	 * 获取请求报文
	 * 
	 * @param msgConf
	 * @param reqDTO
	 * @param <T>
	 * @return
	 */
	public <T> T getReqMsg(MsgConf msgConf, DockingReqDTO reqDTO);
}
