/*
Copyright 2018 All rights reserved.
 */

package com.docking;

import com.docking.config.SendConf;
import com.docking.dto.DockingReqDTO;

/**
 * 发送请求报文
 * 
 * @author miaoqiang
 * @date 2019/1/16.
 */
public interface ISendReqBody<C extends SendConf, D> {

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
