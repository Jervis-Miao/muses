/*
Copyright 2018 All rights reserved.
 */

package com.study.docking;

import com.study.docking.config.SendConf;
import com.study.docking.dto.DockingReqDTO;

/**
 * 发送请求报文
 * 
 * @author miaoqiang
 * @date 2019/1/16.
 */
public interface ISendReqMsg<C extends SendConf, D> {

	/**
	 * 系统信息传输
	 *
	 * @param code
	 * @param sendConf
	 * @param reqData
	 * @param reqDTO
	 * @return
	 */
	public D send(String code, C sendConf, D reqData, DockingReqDTO reqDTO);

	/**
	 * 文件下载
	 *
	 * @param code
	 * @param sendConf
	 * @param reqData
	 * @param reqDTO
	 * @return
	 */
	public byte[] sendForByte(String code, C sendConf, D reqData, DockingReqDTO reqDTO);

}
